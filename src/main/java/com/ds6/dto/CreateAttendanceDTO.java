package com.ds6.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.ds6.enums.AttendanceStatus;

import jakarta.validation.constraints.NotNull;

public record CreateAttendanceDTO(

    @NotNull(message = "Student ID cannot be null")
    UUID studentId,

    @NotNull(message = "Class ID cannot be null")
    UUID classId,

    @NotNull(message = "Date cannot be null")
    LocalDate date,

    @NotNull(message = "Status cannot be null")
    AttendanceStatus status
) {}