package br.com.conexasaude.repositories;

import br.com.conexasaude.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByEmail(String email);

    Patient findByCpf(String cpf);

    List<Patient> findByCpfOrEmail(String cpf, String email);
}
