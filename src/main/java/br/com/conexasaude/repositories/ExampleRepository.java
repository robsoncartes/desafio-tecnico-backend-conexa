package br.com.conexasaude.repositories;

import br.com.conexasaude.models.TestExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExampleRepository extends JpaRepository<TestExampleEntity, Long> {

    @Override
    Optional<TestExampleEntity> findById(Long id);
}
