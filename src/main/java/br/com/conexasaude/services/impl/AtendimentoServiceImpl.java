package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Atendimento;
import br.com.conexasaude.models.Paciente;
import br.com.conexasaude.models.Sintoma;
import br.com.conexasaude.models.enums.Autorizacao;
import br.com.conexasaude.repositories.AtendimentoRepository;
import br.com.conexasaude.repositories.PacienteRepository;
import br.com.conexasaude.repositories.SintomaRepository;
import br.com.conexasaude.security.Login;
import br.com.conexasaude.services.AtendimentoService;
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
public class AtendimentoServiceImpl implements AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private SintomaRepository sintomaRepository;

    @Override
    public Atendimento getById(Long id) {

        Atendimento atendimento = atendimentoRepository.findById(id).orElse(null);

        if (atendimento == null)
            throw new ObjectNotFoundException("Atendimento não encontrado. Id Atendimento: " + id + " não encontrado. Tipo: " + Atendimento.class.getName());

        Login login = loginService.getAuthenticated();

        if (!login.hasRole(Autorizacao.MEDICO) || !Objects.equals(atendimento.getIdMedico(), login.getId()))
            throw new AuthorizationException("Acesso negado.");

        return atendimento;
    }

    @Override
    public List<Atendimento> getByIdPaciente(Long id) {

        return atendimentoRepository.findByIdPaciente(id);
    }

    @Override
    public Atendimento getByIdAndIdPaciente(Long id, Long idPaciente) {

        Atendimento atendimento = atendimentoRepository.findByIdAndIdPaciente(id, idPaciente);

        if (atendimento == null) {
            throw new ObjectNotFoundException("Atendimento não encontrado. Id Atendimento: " + id
                    + ", Id Paciente: " + idPaciente + ". Tipo: " + Atendimento.class.getName());
        }

        return atendimento;
    }


    @Transactional
    @Override
    public Atendimento save(Atendimento atendimento) {

        Paciente paciente = pacienteRepository.findById(atendimento.getIdPaciente()).orElse(null);

        if (paciente == null) {
            throw new DataIntegrityException("Não é possível criar uma atendimento porque o Paciente com Id: " + atendimento.getIdPaciente()
                    + " não existe. " + Atendimento.class.getName());
        }

        Login login = loginService.getAuthenticated();

        if (!login.hasRole(Autorizacao.MEDICO)) {
            throw new AccessDeniedException("Somente Médicos logados podem salvar um atendimento.");
        } else {

            if (atendimento.getDataHora().before(Date.from(Instant.now()))) {
                throw new DataIntegrityException("Não é possível criar um agendamento com a data anterior a data atual.");
            } else {

                atendimento.setId(null);
                atendimento.setDataHora(atendimento.getDataHora());
                atendimento.setIdMedico(login.getId());
                atendimento.setIdPaciente(atendimento.getIdPaciente());

                List<Sintoma> sintomas = atendimento.getSintomas();

                for (Sintoma sintoma : sintomas) {
                    sintoma.setId(null);
                    sintoma.setDescricao(sintoma.getDescricao());
                    sintoma.setDetalhes(sintoma.getDetalhes());
                    sintoma.setAtendimento(atendimento);

                    sintomaRepository.save(sintoma);
                }

                // sintomaRepository.saveAll(sintomas);

                return atendimentoRepository.save(atendimento);
            }
        }
    }

    @Override
    public void delete(Long id) {

        getById(id);

        atendimentoRepository.deleteById(id);
    }

    @Override
    public List<Atendimento> getAll() {
        return atendimentoRepository.findAll();
    }
}
