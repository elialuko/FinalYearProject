package com.example.busbooking;

public class Report {
    String date, time, route, report;

    public Report(){}

    public Report(String date, String time, String route, String report) {
        this.date = date;
        this.time = time;
        this.route = route;
        this.report = report;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
