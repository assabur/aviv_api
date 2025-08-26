package com.aviv.api.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private int status;        // code HTTP (ex: 200)
    private String message;    // message lisible
    private T data;            // payload (ex: List<PropertyDto>)
    @Builder.Default
    private Instant timestamp = Instant.now();

    public static <T> ApiResponse<T> of(int status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}



