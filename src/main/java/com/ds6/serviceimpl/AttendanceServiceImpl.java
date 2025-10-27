package com.ds6.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds6.dto.AttendanceDTO;
import com.ds6.dto.CreateAttendanceDTO;
import com.ds6.model.Attendance;
import com.ds6.repository.AttendanceRepository;
import com.ds6.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public AttendanceDTO createAttendance(CreateAttendanceDTO dto) {
        Attendance attendance = new Attendance();
        attendance.setStudentId(dto.studentId());
        attendance.setClassId(dto.classId());
        attendance.setDate(dto.date());
        attendance.setStatus(dto.status());

        Attendance savedAttendance = attendanceRepository.save(attendance);

        try {
            String topic = "attendance.recorded";
            String payload = savedAttendance.getId().toString();

            kafkaTemplate.send(topic, payload);
            log.info("Event 'attendance.recorded' published for attendance ID: {}", payload);
        } catch (Exception e) {
            log.error("Failed to publish 'attendance.recorded' event for attendance ID: {}. Error: {}", savedAttendance.getId(), e.getMessage());
        }

        return toDTO(savedAttendance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendancesByClassAndDate(UUID classId, LocalDate date) {
        return attendanceRepository.findByClassIdAndDate(classId, date).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendancesByStudent(UUID studentId) {
        return attendanceRepository.findByStudentId(studentId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Método utilitário para converter Entidade para DTO ---
    private AttendanceDTO toDTO(Attendance attendance) {
        return new AttendanceDTO(
            attendance.getId(),
            attendance.getStudentId(),
            attendance.getClassId(),
            attendance.getDate(),
            attendance.getStatus()
        );
    }
}