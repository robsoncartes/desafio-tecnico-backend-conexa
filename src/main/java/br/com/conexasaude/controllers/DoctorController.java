package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping(value = "/doctors/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {

        Doctor doctor = doctorService.getById(id);

        return ResponseEntity.ok().body(doctor);
    }

    @GetMapping(value = "/doctors")
    public ResponseEntity<List<Doctor>> findAll() {

        List<Doctor> doctors = doctorService.getAll();

        return ResponseEntity.ok(doctors);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> save(@Valid @RequestBody Doctor doctor) {

        Doctor obj = doctorService.save(doctor);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
