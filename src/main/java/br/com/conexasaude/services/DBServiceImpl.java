package br.com.conexasaude.services;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.models.TestExampleEntity;
import br.com.conexasaude.repositories.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DBServiceImpl {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ExampleRepository exampleRepository;

    public void instantiateTestDatabase() {

        TestExampleEntity example1 = new TestExampleEntity(1L, "It's just a simple test.");
        TestExampleEntity example2 = new TestExampleEntity(2L, "It's just another simple test.");

        exampleRepository.save(example1);
        exampleRepository.save(example2);

        Doctor doctor1 = new Doctor(null, "medico1@email.com", "pass", "pass", "Cardiologista", "101.202.303-11", "33", "(21) 3232-6565");
        Doctor doctor2 = new Doctor(null, "medico2@email.com", "pass", "pass", "Psiquiatra", "102.202.303-11", "45", "(12) 9000-9111");

        List<Doctor> doctors = new ArrayList<>(Arrays.asList(doctor1, doctor2));

        for (Doctor doctor : doctors)
            doctorService.save(doctor);
    }
}
