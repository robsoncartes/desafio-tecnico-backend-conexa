package br.com.conexasaude.repositories;

import br.com.conexasaude.models.Atendimento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtendimentoRepository extends CrudRepository<Atendimento, Long> {

    @Override
    Optional<Atendimento> findById(Long id);

    @Override
    <S extends Atendimento> S save(S s);

    Atendimento findByIdAndIdPaciente(Long id, Long idPacinte);

    List<Atendimento> findByIdPaciente(Long idPaciente);

    List<Atendimento> findAll();
}
