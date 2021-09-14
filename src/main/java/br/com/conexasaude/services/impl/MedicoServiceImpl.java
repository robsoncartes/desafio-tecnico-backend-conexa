package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Medico;
import br.com.conexasaude.models.enums.Autorizacao;
import br.com.conexasaude.repositories.MedicoRepository;
import br.com.conexasaude.security.Login;
import br.com.conexasaude.services.MedicoService;
import br.com.conexasaude.services.exceptions.AuthorizationException;
import br.com.conexasaude.services.exceptions.DataIntegrityException;
import br.com.conexasaude.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Medico getById(Long id) {

        Medico medico = medicoRepository.findById(id).orElse(null);

        if (medico == null)
            throw new ObjectNotFoundException("Médico não encontrado. Id: " + id + " não encontrado. Tipo: " + Medico.class.getName());

        return medico;
    }

    @Override
    public Medico getByEmail(String email) {

        Login login = loginService.getAuthenticated();

        if (login == null || !login.hasRole(Autorizacao.MEDICO) && !email.equals(login.getEmail()))
            throw new AuthorizationException("Acesso negado.");

        Medico medico = medicoRepository.findByEmail(email);

        if (medico == null)
            throw new ObjectNotFoundException("Médico não encontrado. E-mail: " + email + " não encontrado. Tipo: " + Medico.class.getName());

        return medico;
    }

    @Override
    public List<Medico> getAll() {

        return medicoRepository.findAll();
    }

    @Override
    public Medico save(Medico medico) {

        if (!medico.getSenha().equals(medico.getConfirmacaoSenha()))
            throw new DataIntegrityException("Senha e Confirmação de senha não são iguais.");

        List<Medico> medicos = medicoRepository.findByCpfOrEmail(medico.getCpf(), medico.getEmail());

        for (Medico doc : medicos) {
            System.err.println(doc.getCpf() + " " + doc.getEmail());
        }

        if (medicos.isEmpty()) {
            medico.setId(null);
            medico.setEmail(medico.getEmail());
            medico.setSenha(passwordEncoder.encode(medico.getSenha()));
            medico.setConfirmacaoSenha(medico.getConfirmacaoSenha());
            medico.setEspecialidade(medico.getEspecialidade());
            medico.setCpf(medico.getCpf());
            medico.setIdade(medico.getIdade());
            medico.setTelefone(medico.getTelefone());

            return medicoRepository.save(medico);
        }

        if (medicos.size() == 2) {
            throw new DataIntegrityException("CPF e E-mail já estão sendo utilizados.");
        }

        Medico doc = medicos.get(0);

        if (medico.getCpf().equals(doc.getCpf()) && medico.getEmail().equals(doc.getEmail()))
            throw new DataIntegrityException("Já existe um cadastro com o CPF e E-mail informados.");

        if (medico.getCpf().equals(doc.getCpf()))
            throw new DataIntegrityException("Já existe um cadastro com o CPF informado.");
        else
            throw new DataIntegrityException("Já existe um cadastro com o E-mail informado.");
    }
}
