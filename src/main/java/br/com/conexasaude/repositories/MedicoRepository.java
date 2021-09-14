package br.com.conexasaude.repositories;

import br.com.conexasaude.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Medico findByEmail(String email);

    List<Medico> findByCpfOrEmail(String cpf, String email);
}
