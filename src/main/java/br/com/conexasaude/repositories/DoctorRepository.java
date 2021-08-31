package br.com.conexasaude.repositories;

import br.com.conexasaude.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByEmail(String email);

    Doctor findByCpf(String cpf);

    Doctor findByEmailOrCpf(String email, String cpf);
}
