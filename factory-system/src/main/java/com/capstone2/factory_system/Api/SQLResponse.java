package com.capstone2.factory_system.Api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SQLResponse {
    private String answer;
    @JsonProperty("sql_query")
    private String sqlQuery;
    @JsonProperty("sql_result")
    private String sqlResult;
}
