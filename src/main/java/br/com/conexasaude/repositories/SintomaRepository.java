package br.com.conexasaude.repositories;

import br.com.conexasaude.models.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface SintomaRepository extends JpaRepository<Sintoma, Integer> {

    @Override
    <S extends Sintoma> S save(S sintoma);
}
