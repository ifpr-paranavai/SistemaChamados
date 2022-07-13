package com.api.sistemachamados.utils;

import com.api.sistemachamados.dto.ApiResponseDTO;
import com.api.sistemachamados.dto.TokenDTO;
import org.springframework.http.HttpStatus;

public class utils {

    public static ApiResponseDTO<Object> setHandlerResponse(Object obj, HttpStatus http, String mensagem) {
        var apiResponseDTO = new ApiResponseDTO<>();
        apiResponseDTO.setData(obj);
        apiResponseDTO.setStatus(http);
        apiResponseDTO.setMessage(mensagem);
        return apiResponseDTO;
    }
}
