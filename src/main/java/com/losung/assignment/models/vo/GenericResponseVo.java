package com.losung.assignment.models.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Data
@ToString
public class GenericResponseVo {

    private Boolean success;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errors;

    private Object data;
}
