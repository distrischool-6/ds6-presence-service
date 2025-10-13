package com.ds6.dto;

import com.ds6.enums.AttendanceStatus;
import java.time.LocalDate;
import java.util.UUID;

// DTO para representar um registo de presença nas respostas da API
public record AttendanceDTO(
    UUID id,
    UUID studentId,
    UUID classId,
    LocalDate date,
    AttendanceStatus status
) {}