package com.fuakim.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {

    private int status;
    private String message;
    private List<T> data;


    public GenericResponse<T> ok(List<T> data){
        return new GenericResponse<>(200,"succes",data);
    }
    public GenericResponse<T> created(List<T> data){
        return new GenericResponse<>(201,"created",data);
    }
}
