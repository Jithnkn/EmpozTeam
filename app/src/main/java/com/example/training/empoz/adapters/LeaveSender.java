package com.example.training.empoz.adapters;

public class LeaveSender {
    public String reason, comments, dateFrom, dateTo, userId, name;

    public LeaveSender(String reason, String dateFrom, String dateTo, String name) {
        this.reason = reason;
   this.name=name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

    }

    public LeaveSender() {

    }


    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }



    public String getDateFrom() {
        return dateFrom;
    }

    public String getUserId() {
        return userId;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

