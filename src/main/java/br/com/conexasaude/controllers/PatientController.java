package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Patient;
import br.com.conexasaude.models.views.DoctorView;
import br.com.conexasaude.models.views.PatientView;
import br.com.conexasaude.services.PatientService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping(value = "/patients/{id}")
    @JsonView(PatientView.PatientComplete.class)
    public ResponseEntity<Patient> findById(@PathVariable Long id) {

        Patient patient = patientService.getById(id);

        return ResponseEntity.ok().body(patient);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping(value = "/patients")
    @JsonView(PatientView.PatientComplete.class)
    public ResponseEntity<List<Patient>> findAll() {

        List<Patient> patients = patientService.getAll();

        return ResponseEntity.ok(patients);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping(value = "/patients")
    public ResponseEntity<Void> save(@Valid @RequestBody Patient patient){

        Patient obj = patientService.save(patient);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
