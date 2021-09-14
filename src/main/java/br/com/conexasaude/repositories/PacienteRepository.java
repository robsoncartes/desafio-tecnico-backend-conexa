package br.com.conexasaude.repositories;

import br.com.conexasaude.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Paciente findByEmail(String email);

    Paciente findByCpf(String cpf);

    List<Paciente> findByCpfOrEmail(String cpf, String email);
}
