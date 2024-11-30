package com.example.ecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class ApiPagiableResponse {
    private String message;
    private Object data ;
    private Object payload;

    public ApiPagiableResponse(String message,Object data,Object payload){
        this.message = message;
        this.data= data;
        this.payload = payload;
    }
}
