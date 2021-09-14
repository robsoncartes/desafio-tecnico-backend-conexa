package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Paciente;
import br.com.conexasaude.repositories.PacienteRepository;
import br.com.conexasaude.services.PacienteService;
import br.com.conexasaude.services.exceptions.DataIntegrityException;
import br.com.conexasaude.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private HttpServletRequest servletRequest;

    @Override
    public Paciente getById(Long id) {

        Paciente paciente = pacienteRepository.findById(id).orElse(null);

        if (paciente == null)
            throw new ObjectNotFoundException("Paciente não encontrado. Id: " + id + ". Tipo: " + Paciente.class.getName());

        return paciente;
    }

    @Override
    public Paciente getByEmail(String email) {

        Paciente paciente = pacienteRepository.findByEmail(email);

        if (paciente == null)
            throw new ObjectNotFoundException("Paciente não encontrado. Email: " + email + ". Tipo: " + Paciente.class.getName());

        return paciente;
    }

    @Override
    public Paciente save(Paciente paciente) {

        List<Paciente> pacientes = pacienteRepository.findByCpfOrEmail(paciente.getCpf(), paciente.getEmail());

        for (Paciente pat : pacientes)
            System.err.println(pat.getCpf() + " " + pat.getEmail());

        if (pacientes.isEmpty()) {
            paciente.setId(null);
            paciente.setNome(paciente.getNome());
            paciente.setCpf(paciente.getCpf());
            paciente.setIdade(paciente.getIdade());
            paciente.setEmail(paciente.getEmail());
            paciente.setTelefone(paciente.getTelefone());

            return pacienteRepository.save(paciente);
        }

        if (pacientes.size() == 2) {
            throw new DataIntegrityException("CPF e  E-mail já estão sendo utilizados.");
        }

        Paciente pat = pacientes.get(0);

        if (paciente.getCpf().equals(pat.getCpf()) && paciente.getEmail().equals(pat.getEmail()))
            throw new DataIntegrityException("Já existe um Paciente com o CPF e E-mail informados.");

        if (paciente.getCpf().equals(pat.getCpf()))
            throw new DataIntegrityException("Já existe um Paciente com o CFP informado.");

        else
            throw new DataIntegrityException("Já existe um Paciente com o E-mail informado.");
    }

    @Override
    public Paciente update(Paciente paciente) {

        /**
         * TODO
         * Not working as well. It's not updating as it should. Try other implementation and modeling alternatives.
         * Note: use DTO if the difficulty with JsonView persists.
         */

        Map<String, String> map = (Map<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long uriId = Long.parseLong(map.get("id"));

        Paciente novoPaciente = getByEmail(paciente.getEmail());

        if (novoPaciente != null && novoPaciente.getId().equals(uriId)) {
            updateData(novoPaciente, paciente);
            return pacienteRepository.save(novoPaciente);
        }

        if (novoPaciente != null && novoPaciente.getCpf().equals(paciente.getCpf())) {
            throw new DataIntegrityException("CPF já existe");
        } else {
            throw new DataIntegrityException("SEI LA Já existe um cadastro com o Email informado.");
        }
    }

    @Override
    public void delete(Long id) {

        getById(id);

        try {
            pacienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Paciente com Agendamentos.");
        }
    }

    @Override
    public void updateData(Paciente novoPaciente, Paciente paciente) {

        novoPaciente.setNome(paciente.getNome());
        novoPaciente.setCpf(paciente.getCpf());
        novoPaciente.setIdade(paciente.getIdade());
        novoPaciente.setEmail(paciente.getEmail());
        novoPaciente.setTelefone(paciente.getTelefone());
    }

    @Override
    public List<Paciente> getAll() {

        return pacienteRepository.findAll();
    }
}
