package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DBServiceImpl {

    @Autowired
    private DoctorService doctorService;

    public void instantiateTestDatabase() {

        Doctor doctor1 = new Doctor(null, "medico1@email.com", "pass", "pass", "Cardiologista", "101.202.303-11", "33", "(21) 3232-6565");
        Doctor doctor2 = new Doctor(null, "medico2@email.com", "pass", "pass", "Psiquiatra", "102.202.303-11", "45", "(12) 9000-9111");

        List<Doctor> doctors = new ArrayList<>(Arrays.asList(doctor1, doctor2));

        for (Doctor doctor : doctors)
            doctorService.save(doctor);
    }
}
