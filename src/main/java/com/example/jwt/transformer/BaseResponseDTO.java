package com.example.jwt.transformer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BaseResponseDTO<T> {

    private int status;
    private T data;

    public BaseResponseDTO(T data, int status){
        this.data = data;
        this.status = status;
    }
}
