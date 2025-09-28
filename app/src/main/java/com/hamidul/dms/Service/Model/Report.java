package com.hamidul.dms.Service.Model;

import org.json.JSONObject;

public class Report {
    double totalNetAmount;
    double totalDamage;
    double totalCommission;
    double totalCash;
    double totalDue;

    private Report() {
    }

    public static Report fromJson(JSONObject object) {
        Report report = new Report();
        report.totalNetAmount = object.optDouble("total_amount");
        report.totalDamage = object.optDouble("total_damage");
        report.totalCommission = object.optDouble("total_commission");
        report.totalCash = object.optDouble("total_cash");
        double sumDCC = report.totalDamage + report.totalCommission + report.totalCash;
        report.totalDue = report.totalNetAmount - sumDCC;
        return report;
    }

    public double getTotalNetAmount() {
        return totalNetAmount;
    }

    public void setTotalNetAmount(double totalNetAmount) {
        this.totalNetAmount = totalNetAmount;
    }

    public double getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(double totalDamage) {
        this.totalDamage = totalDamage;
    }

    public double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(double totalCash) {
        this.totalCash = totalCash;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(double totalDue) {
        this.totalDue = totalDue;
    }
}
