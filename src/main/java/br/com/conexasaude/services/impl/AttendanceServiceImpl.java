package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Attendance;
import br.com.conexasaude.models.Patient;
import br.com.conexasaude.models.enums.AuthorityName;
import br.com.conexasaude.repositories.AttendanceRepository;
import br.com.conexasaude.repositories.DoctorRepository;
import br.com.conexasaude.repositories.PatientRepository;
import br.com.conexasaude.security.Login;
import br.com.conexasaude.services.AttendanceService;
import br.com.conexasaude.services.LoginService;
import br.com.conexasaude.services.exceptions.AuthorizationException;
import br.com.conexasaude.services.exceptions.DataIntegrityException;
import br.com.conexasaude.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LoginService loginService;

    @Override
    public Attendance getById(Long id) {

        Attendance attendance = attendanceRepository.findById(id).orElse(null);

        if (attendance == null)
            throw new ObjectNotFoundException("Atendimento não encontrado. Id: " + id + ", Tipo: " + Attendance.class.getName());

        Login login = loginService.getAuthenticated();

        if (!login.hasRole(AuthorityName.DOCTOR) || !Objects.equals(attendance.getDoctorId(), login.getId()))
            throw new AuthorizationException("Acesso negado.");

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
            throw new ObjectNotFoundException("Atendimento não encontrado. Id Atendimento: " + id
                    + ", Id Paciente: " + patientId + ", Tipo: " + Attendance.class.getName());
        }

        return attendance;
    }


    @Transactional
    @Override
    public Attendance save(Attendance attendance) {

        Patient patient = patientRepository.findById(attendance.getPatientId()).orElse(null);

        if (patient == null) {
            throw new DataIntegrityException("Não é possível criar uma atendimento porque o Paciente com Id: " + attendance.getPatientId()
                    + " não existe. " + Attendance.class.getName());
        }

        Login login = loginService.getAuthenticated();

        if (!login.hasRole(AuthorityName.DOCTOR)) {
            throw new AccessDeniedException("Somente Médicos logados podem salvar um atendimento.");
        } else {

            if (attendance.getInstant().before(Date.from(Instant.now()))) {
                System.err.println(attendance.getInstant());
                System.err.println(Date.from(Instant.now()));
                throw new DataIntegrityException("Não é possível criar um agendamento com a data anterior a data atual.");
            } else {

                attendance.setId(null);
                attendance.setInstant(attendance.getInstant());
                attendance.setDoctorId(login.getId());
                attendance.setPatientId(attendance.getPatientId());

                System.err.println("Hora agora: " + Date.from(Instant.now()));
                System.err.println(attendance);

                return attendanceRepository.save(attendance);

            }
        }
    }

    @Override
    public void delete(Long id) {

        getById(id);

        attendanceRepository.deleteById(id);
    }

    @Override
    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }
}
