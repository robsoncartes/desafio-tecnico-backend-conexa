package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.models.views.DoctorView;
import br.com.conexasaude.security.JWTUtil;
import br.com.conexasaude.security.Login;
import br.com.conexasaude.services.DoctorService;
import br.com.conexasaude.services.LoginService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class DoctorController {

    private JWTUtil jwtUtil;
    private LoginService loginService;
    private DoctorService doctorService;

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping(value = "/doctors/{id}")
    @JsonView(DoctorView.DoctorComplete.class)
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {

        Doctor doctor = doctorService.getById(id);

        return ResponseEntity.ok().body(doctor);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> save(@Valid @RequestBody Doctor doctor) {

        Doctor obj = doctorService.save(doctor);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        System.err.println(uri);
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping(value = "/logoff")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {

        Login login = loginService.getAuthenticated();
        String token = jwtUtil.generateToken(login.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(value = "/doctors")
    @JsonView(DoctorView.DoctorComplete.class)
    public ResponseEntity<List<Doctor>> findAll() {

        List<Doctor> doctors = doctorService.getAll();

        return ResponseEntity.ok(doctors);
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
