package br.com.conexasaude.repositories;

import br.com.conexasaude.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByEmail(String email);

    Doctor findByCpf(String cpf);

    Doctor findByEmailOrCpf(String email, String cpf);

    List<Doctor> findByCpfOrEmail(String cpf, String email);
}
