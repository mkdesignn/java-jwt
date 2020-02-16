package com.example.jwt.transformer;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ValidationDTO {

    private List errors;
}
