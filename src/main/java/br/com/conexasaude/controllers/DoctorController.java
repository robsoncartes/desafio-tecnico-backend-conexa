package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.models.views.DoctorView;
import br.com.conexasaude.services.DoctorService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping(value = "/doctors/{id}")
    @JsonView(DoctorView.DoctorComplete.class)
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {

        Doctor doctor = doctorService.getById(id);

        return ResponseEntity.ok().body(doctor);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(value = "/doctors")
    @JsonView(DoctorView.DoctorComplete.class)
    public ResponseEntity<List<Doctor>> findAll() {

        List<Doctor> doctors = doctorService.getAll();

        return ResponseEntity.ok(doctors);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> save(@Valid @RequestBody Doctor doctor) {

        Doctor obj = doctorService.save(doctor);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        System.err.println(uri);
        return ResponseEntity.created(uri).build();
    }

    // testando validação - remover a função handleValidation após concluir o teste
    /*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);

        });

        return errors;
    }

     */
}
