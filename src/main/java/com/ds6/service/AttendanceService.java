package com.ds6.service;

import com.ds6.dto.AttendanceDTO;
import com.ds6.dto.CreateAttendanceDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    public AttendanceDTO createAttendance(CreateAttendanceDTO createAttendanceDTO);
    public List<AttendanceDTO> getAttendancesByClassAndDate(UUID classId, LocalDate date);
    public List<AttendanceDTO> getAttendancesByStudent(UUID studentId);
}