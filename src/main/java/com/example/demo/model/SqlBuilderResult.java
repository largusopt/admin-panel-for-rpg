package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class SqlBuilderResult {
    String sql;
    List<Object> clausesParams;

    public SqlBuilderResult(String sql, List<Object> clausesParams) {
        this.sql = sql;
        this.clausesParams = clausesParams;
    }
}
