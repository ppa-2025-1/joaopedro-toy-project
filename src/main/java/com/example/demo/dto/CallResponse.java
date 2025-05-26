package com.example.demo.dto;

import com.example.demo.model.entity.Call;
import com.example.demo.model.entity.Call.CallStatus;

import java.time.LocalDateTime;

public record CallResponse(
        Integer id,
        String action,
        String object,
        String details,
        CallStatus status,
        Integer userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public CallResponse(Call call) {
        this(
                call.getId(),
                call.getAction(),
                call.getObject(),
                call.getDetails(),
                call.getStatus(),
                call.getUser() != null ? call.getUser().getId() : null,
                call.getCreatedAt(),
                call.getUpdatedAt()
        );
    }
}
