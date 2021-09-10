package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Attendance;
import br.com.conexasaude.models.Doctor;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
            throw new AuthorizationException("Acesso negado. Somente o Médico que criou o atendimento pode visualizá-lo.");

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
        Doctor doctor = doctorRepository.findById(attendance.getDoctorId()).orElse(null);

        if (patient == null && doctor == null) {
            throw new DataIntegrityException("Não é possível criar uma atendimento porque o Médico com Id: "
                    + attendance.getDoctorId() + " e o Paciente com Id: " + attendance.getPatientId()
                    + " não existem. " + Attendance.class.getName());
        }

        if (patient == null) {
            throw new DataIntegrityException("Não é possível criar um atendimento porque o Paciente com Id: "
                    + attendance.getPatientId() + " naõ existe. " + Attendance.class.getName());
        }

        if (doctor == null) {
            throw new DataIntegrityException("Não é possível criar um atendimento porque o Médico com Id: "
                    + attendance.getDoctorId() + " não existe. " + Attendance.class.getName());
        } else {
            attendance.setId(null);
            attendance.setInstant(new Date());
            attendance.setPatientId(attendance.getPatientId());

            System.err.println("Atendimento Id: " + attendance.getId() + "\tPaciente: " + patient);
            System.err.println(attendance);

            return attendanceRepository.save(attendance);
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
