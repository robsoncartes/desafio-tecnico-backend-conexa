package br.com.conexasaude.repositories;

import br.com.conexasaude.models.Attendance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance, Long> {

    @Override
    Optional<Attendance> findById(Long id);

    @Override
    <S extends Attendance> S save(S s);

    Attendance findByIdAndPatientId(Long id, Long patientId);

    List<Attendance> findByPatientId(Long patientId);

    List<Attendance> findAll();
}
