package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.models.Patient;
import br.com.conexasaude.services.AttendanceService;
import br.com.conexasaude.services.DBService;
import br.com.conexasaude.services.DoctorService;
import br.com.conexasaude.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DBServiceImpl implements DBService {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AttendanceService attendanceService;

    @Override
    public void instantiateTestDatabase() throws Exception {

        Doctor doctor1 = new Doctor(null, "medico1@email.com", "pass", "pass", "Cardiologista", "782.743.770-28", "33", "(21) 1111-1111");
        Doctor doctor2 = new Doctor(null, "medico2@email.com", "pass", "pass", "Psiquiatra", "435.329.460-17", "45", "(21) 1111-2222");

        List<Doctor> doctors = new ArrayList<>(Arrays.asList(doctor1, doctor2));

        for (Doctor doctor : doctors)
            doctorService.save(doctor);

        Patient patient1 = new Patient(null, "Patient1", "744.197.940-46", "42", "patient1@email.com", "(12) 2222-1111");
        Patient patient2 = new Patient(null, "Patient2", "602.327.740-30", "31", "patient2@email.com", "(12) 2222-2222");
        Patient patient3 = new Patient(null, "Patient3", "686.309.940-37", "31", "patient3@email.com", "(12) 2222-3333");

        List<Patient> patients = new ArrayList<>(Arrays.asList(patient1, patient2, patient3));

        for (Patient patient : patients)
            patientService.save(patient);
    }
}
