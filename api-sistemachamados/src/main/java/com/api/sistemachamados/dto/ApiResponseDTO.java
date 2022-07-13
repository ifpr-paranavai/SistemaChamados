package com.api.sistemachamados.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ApiResponseDTO<T> {
    @JsonProperty
    private HttpStatus status;
    @JsonProperty
    private Boolean success;
    @JsonProperty
    private String message;
    @JsonProperty
    private T data;

    public int getStatusValue() {
        return status.value();
    }

    public ApiResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
        this.success = status.is2xxSuccessful();
    }

    public void setSuccess(boolean success) {}
}
