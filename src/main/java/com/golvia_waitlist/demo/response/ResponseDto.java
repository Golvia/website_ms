package com.golvia_waitlist.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private List<String> errors;
}
