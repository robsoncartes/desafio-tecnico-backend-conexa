package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Medico;
import br.com.conexasaude.models.Paciente;
import br.com.conexasaude.services.DBService;
import br.com.conexasaude.services.MedicoService;
import br.com.conexasaude.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DBServiceImpl implements DBService {

    @Autowired
    private MedicoService doctorService;

    @Autowired
    private PacienteService patientService;

    @Override
    public void instantiateTestDatabase() throws Exception {

        Medico medico1 = new Medico(null, "medico1@email.com", "pass", "pass", "Cardiologista", "782.743.770-28", "33", "(21) 1111-1111");
        Medico medico2 = new Medico(null, "medico2@email.com", "pass", "pass", "Psiquiatra", "435.329.460-17", "45", "(21) 1111-2222");

        List<Medico> medicos = new ArrayList<>(Arrays.asList(medico1, medico2));

        for (Medico medico : medicos)
            doctorService.save(medico);

        Paciente paciente1 = new Paciente(null, "Paciente 1", "744.197.940-46", "42", "paciente1@email.com", "(12) 2222-1111");
        Paciente paciente2 = new Paciente(null, "Paciente 2", "602.327.740-30", "31", "paciente2@email.com", "(12) 2222-2222");
        Paciente paciente3 = new Paciente(null, "Paciente 3", "686.309.940-37", "31", "paciente3@email.com", "(12) 2222-3333");

        List<Paciente> pacientes = new ArrayList<>(Arrays.asList(paciente1, paciente2, paciente3));

        for (Paciente paciente : pacientes)
            patientService.save(paciente);
    }
}
