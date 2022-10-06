package com.blogapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.SecondaryTable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter

public class Filter {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateFrom;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateTo;

//    public String getStartPublishedDate() {
//        return startPublishedDate == null ? "0000-00-00" : startPublishedDate;
//    }
//
//    public void setStartPublishedDate(String startPublishedDate) {
//        this.startPublishedDate = startPublishedDate;
//    }
//
//    public String getEndPublishedDate() {
//        return endPublishedDate == null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date()) : endPublishedDate;
//    }
//
//    public void setEndPublishedDate(String endPublishedDate) {
//        this.endPublishedDate = endPublishedDate;
//    }
//
//    @Override
//    public String toString() {
//        return "Filter{" +
//                "startPublishedDate='" + startPublishedDate + '\'' +
//                ", endPublishedDate='" + endPublishedDate + '\'' +
//                '}';
//    }
}
