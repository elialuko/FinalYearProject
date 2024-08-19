import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
import datetime as dt
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import pandas as pd
from datetime import datetime, timedelta
from flask import Flask, jsonify

cred = credentials.Certificate('firebase-adminsdk.json')
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://busbooking-72abe-default-rtdb.firebaseio.com'
})



app = Flask(__name__)


def getDay(date_string):
    date_object = datetime.strptime(date_string, "%d-%m-%Y")
    day_of_week = date_object.strftime("%A")
    return day_of_week


bookings_list = []
reports_list = []
users_ref = db.reference('User')
users_data = users_ref.get()

for user_id, user_data in users_data.items():
    if 'Bookings' in user_data:
        for booking_id, booking_data in user_data['Bookings'].items():
            if 'date' not in booking_data:
                continue
            else:
                if 'day' not in booking_data:
                    booking_data.update({'day': getDay(booking_data['date'])})
                booking_data.update({'user_id': user_id, 'booking_id': booking_id})
                bookings_list.append(booking_data)
    
    if 'Reports' in user_data:
        for report_id, report_data in user_data['Reports'].items():
            report_data.update({'user_id': user_id, 'report_id': report_id})
            reports_list.append(report_data)
bookings_df = pd.DataFrame(bookings_list)
reports_df = pd.DataFrame(reports_list)
bookings_data = bookings_df.drop(columns=['user_id', 'booking_id','id','email','status'])
bookings_df = bookings_df.drop(columns=['email','id','name','status','user_id','booking_id','price'])


bookings_data['date'] = pd.to_datetime(bookings_data['date'], format='%d-%m-%Y', errors='coerce', dayfirst=True)
daily_bookings = bookings_data.groupby('date').size().reset_index(name='bookings_count')
daily_bookings['day_of_week'] = daily_bookings['date'].dt.dayofweek
daily_bookings['month'] = daily_bookings['date'].dt.month
daily_bookings['year'] = daily_bookings['date'].dt.year


X = daily_bookings[['day_of_week', 'month', 'year']]
y = daily_bookings['bookings_count']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
model = LinearRegression()
model.fit(X_train, y_train)


current_date = dt.datetime.now().date()
future_dates_today = [current_date + dt.timedelta(days=x) for x in range(22)]
future_data_today = pd.DataFrame(future_dates_today, columns=['date'])
future_data_today['date'] = pd.to_datetime(future_data_today['date'])  
future_data_today['day_of_week'] = future_data_today['date'].dt.dayofweek
future_data_today['month'] = future_data_today['date'].dt.month
future_data_today['year'] = future_data_today['date'].dt.year
future_data_today['day_of_week'] = future_data_today['date'].dt.dayofweek
future_data_today['predicted_bookings'] = np.ceil(model.predict(future_data_today[['day_of_week', 'month', 'year']]))
future_data_today['day_of_week'] = future_data_today['date'].apply(lambda x: x.strftime('%A'))


bookings_grouped = bookings_df.groupby(['day', 'to']).size().reset_index(name='bookings')
all_days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
all_destinations = bookings_df['to'].unique()
full_index = pd.MultiIndex.from_product([all_days, all_destinations], names=['day', 'to'])
bookings_full = bookings_grouped.set_index(['day', 'to']).reindex(full_index, fill_value=0).reset_index()


bookings_grouped2 = bookings_df.groupby(['day', 'from']).size().reset_index(name='bookings')
all_destinations2 = bookings_df['from'].unique()
full_index2 = pd.MultiIndex.from_product([all_days, all_destinations2], names=['day', 'from'])
bokings_full2 = bookings_grouped2.set_index(['day', 'from']).reindex(full_index2, fill_value=0).reset_index()

@app.route('/future_bookings', methods=['GET'])
def future_bookings():
    return jsonify(future_data_today[['date', 'day_of_week', 'predicted_bookings']].to_dict(orient='records'))

@app.route('/future_stops_to', methods=['GET'])
def popular_stops():
        return jsonify(bookings_full.to_dict())

@app.route('/future_stops_from', methods=['GET'])
def popular_tickets_stops():
    return jsonify(bokings_full2.to_dict())


if __name__ == '__main__':
    app.run(host='0.0.0.0' ,debug=True)
