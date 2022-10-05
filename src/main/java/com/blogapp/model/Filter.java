package com.blogapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Filter {

    private String startPublishedDate;
    private String endPublishedDate;

    public String getStartPublishedDate() {
        return startPublishedDate == null ? "0000-00-00" : startPublishedDate;
    }

    public void setStartPublishedDate(String startPublishedDate) {
        this.startPublishedDate = startPublishedDate;
    }

    public String getEndPublishedDate() {
        return endPublishedDate == null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date()) : endPublishedDate;
    }

    public void setEndPublishedDate(String endPublishedDate) {
        this.endPublishedDate = endPublishedDate;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "startPublishedDate='" + startPublishedDate + '\'' +
                ", endPublishedDate='" + endPublishedDate + '\'' +
                '}';
    }
}
