package br.com.conexasaude.services;

import br.com.conexasaude.models.Attendance;

import java.util.List;

public interface AttendanceService {

    Attendance getById(Long id);

    List<Attendance> getByPatient(Long id);

    Attendance getByIdAndPatientId(Long id, Long patientId);

    Attendance save(Attendance attendance);

    List<Attendance> getAll();
}
