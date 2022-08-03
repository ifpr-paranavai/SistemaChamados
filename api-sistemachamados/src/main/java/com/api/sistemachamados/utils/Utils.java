package com.api.sistemachamados.utils;

import com.api.sistemachamados.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Utils {

    public static ApiResponseDTO<Object> setHandlerResponse(Object obj, HttpStatus http, String mensagem) {
        var apiResponseDTO = new ApiResponseDTO<>();
        apiResponseDTO.setData(obj);
        apiResponseDTO.setStatus(http);
        apiResponseDTO.setMessage(mensagem);
        return apiResponseDTO;
    }
}
