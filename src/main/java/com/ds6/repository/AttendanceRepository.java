package com.ds6.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds6.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    List<Attendance> findByClassIdAndDate(UUID classId, LocalDate date);
    List<Attendance> findByStudentId(UUID studentId);
}
