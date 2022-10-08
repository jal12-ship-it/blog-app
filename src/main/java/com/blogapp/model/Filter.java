package com.blogapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
public class Filter {

    private String dateFrom;
    private String dateTo;
    private String[] tagId;
    private String[] authorId;

    public LocalDate getLocalDateTo(String date) {
        if (date != null) {
            return date.isEmpty() ? LocalDate.now() : LocalDate.parse(date);
        }
        return LocalDate.now();
    }

    public LocalDate getLocalDateFrom(String date) {
        if (date != null) {
            return date.isEmpty() ? LocalDate.of(2022, 10, 1) : LocalDate.parse(date);
        }
        return LocalDate.of(2022, 10, 1);
    }

    public List<String> getNameList(String[] idList, String[] allList) {
        List<String> nameList = idList == null ? Arrays.asList(allList) : new ArrayList<>();

        if (idList != null) {
            Arrays.stream(idList)
                    .map(id -> allList[Integer.parseInt(id)])
                    .forEach(nameList::add);
        }
        return nameList;
    }

    public boolean isValid() {
        return dateFrom != null || dateTo != null || tagId != null || authorId != null;
    }

}
