package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Attendance;
import br.com.conexasaude.models.Patient;
import br.com.conexasaude.repositories.AttendanceRepository;
import br.com.conexasaude.repositories.PatientRepository;
import br.com.conexasaude.services.AttendanceService;
import br.com.conexasaude.services.exceptions.DataIntegrityException;
import br.com.conexasaude.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Attendance getById(Long id) {

        Attendance attendance = attendanceRepository.findById(id).orElse(null);

        if (attendance == null)
            throw new ObjectNotFoundException("Atendimento não encontrado. Id: " + Attendance.class.getName());

        return attendance;
    }

    @Override
    public List<Attendance> getByPatient(Long id) {

        return attendanceRepository.findByPatientId(id);
    }

    @Override
    public Attendance getByIdAndPatientId(Long id, Long patientId) {

        Attendance attendance = attendanceRepository.findByIdAndPatientId(id, patientId);

        if (attendance == null) {
            throw new ObjectNotFoundException("Atendimento não encontrado. Id: " + id
                    + ", Id Paciente: " + patientId + ", " + Attendance.class.getName());
        }

        return attendance;
    }


    @Transactional
    @Override
    public Attendance save(Attendance attendance) {

        Patient patient = patientRepository.findById(attendance.getPatientId()).orElse(null);

        if (patient == null) {
            throw new DataIntegrityException("Não é possível criar um atendimento porque o Paciente com Id: "
                    + attendance.getPatientId() + " naõ existe. " + Attendance.class.getName());
        } else {
            System.err.println("Atendimento Id: " + attendance.getId() + "\tPaciente: " + patient);
            attendance.setId(null);
            attendance.setInstant(new Date());
            attendance.setPatientId(attendance.getPatientId());

            return attendanceRepository.save(attendance);
        }
    }

    @Override
    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

}
