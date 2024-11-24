package com.ti9.send.email.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataListWrapper<T> {
    private int status;
    private String message;
    private int totalData;
    private int totalPage;
    private int currentPage;
    private List<T> dataList;

    public DataListWrapper(List<T> dataList) {
        this.dataList = dataList;
    }
}
