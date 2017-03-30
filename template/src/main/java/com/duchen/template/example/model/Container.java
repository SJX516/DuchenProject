package com.duchen.template.example.model;

import com.duchen.template.example.request.result.Pagination;

import java.util.List;

public interface Container {

    Pagination getPagination();

    long getId();

    String getDesc();

    String getName();

    List<Item> getItems();
}
