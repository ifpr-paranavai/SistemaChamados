package com.api.sistemachamados.utils;

import com.api.sistemachamados.dto.ApiResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Utils {

    public static ApiResponseDTO<Object> setHandlerResponse(Object obj, HttpStatus http, String mensagem) {
        var apiResponseDTO = new ApiResponseDTO<>();
        apiResponseDTO.setData(obj);
        apiResponseDTO.setStatus(http);
        apiResponseDTO.setMessage(mensagem);
        return apiResponseDTO;
    }


    public static String[] getNomeAtributosNullos(Object objeto) {
        final BeanWrapper src = new BeanWrapperImpl(objeto);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copiarAtributosIgnorandoNullos(Object objOrigem, Object objDestino) {
        BeanUtils.copyProperties(objOrigem, objDestino, getNomeAtributosNullos(objOrigem));
    }
}
