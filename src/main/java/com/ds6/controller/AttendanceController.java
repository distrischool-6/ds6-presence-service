package com.ds6.controller;

import com.ds6.dto.AttendanceDTO;
import com.ds6.dto.CreateAttendanceDTO;
import com.ds6.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody CreateAttendanceDTO createAttendanceDTO) {
        AttendanceDTO createdAttendance = attendanceService.createAttendance(createAttendanceDTO);
        return new ResponseEntity<>(createdAttendance, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAttendances(
            @RequestParam(required = false) UUID classId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) UUID studentId) {

        if (classId != null && date != null) {
            // Busca a lista de chamada de uma turma numa data específica
            List<AttendanceDTO> attendances = attendanceService.getAttendancesByClassAndDate(classId, date);
            return ResponseEntity.ok(attendances);
        }

        if (studentId != null) {
            // Busca o histórico de presenças de um aluno
            List<AttendanceDTO> attendances = attendanceService.getAttendancesByStudent(studentId);
            return ResponseEntity.ok(attendances);
        }

        // Se nenhum parâmetro válido for fornecido, retorna uma lista vazia ou um erro 400.
        return ResponseEntity.badRequest().build();
    }
}