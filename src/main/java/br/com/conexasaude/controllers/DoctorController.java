package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.models.views.DoctorView;
import br.com.conexasaude.security.JWTUtil;
import br.com.conexasaude.security.Login;
import br.com.conexasaude.services.DoctorService;
import br.com.conexasaude.services.LoginService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Obtenha um médico com base em seu ID", response = Doctor.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Médico recuperado com sucesso."),
            @ApiResponse(code = 401, message = "Não autorizado."),
            @ApiResponse(code = 403, message = "Proibido. Você não tem permissão para acessar esse recurso."),
            @ApiResponse(code = 404, message = "O recurso não foi encontrado."),
            @ApiResponse(code = 500, message = "O servidor encontrou uma condição inesperada e que o impediu de atender à solicitação.")})
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {

        Doctor doctor = doctorService.getById(id);

        return ResponseEntity.ok().body(doctor);
    }

    @ApiOperation(value = "Salvar um novo médico no banco de dados", response = Doctor.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Recurso criado com sucesso. Médico salvo no banco de dados."),
            @ApiResponse(code = 400, message = "Pedido ruim. O servidor não pode ou não irá processar a requisição devido a alguma coisa que foi entendida como um erro."),
            @ApiResponse(code = 401, message = "Não autorizado."),
            @ApiResponse(code = 403, message = "Proibido. Você não tem permissão para acessar esse recurso."),
            @ApiResponse(code = 404, message = "O recurso não foi encontrado."),
            @ApiResponse(code = 422, message = "Entidade não processável."),
            @ApiResponse(code = 500, message = "O servidor encontrou uma condição inesperada e que o impediu de atender à solicitação.")
    })
    @PostMapping(value = "/signup")
    public ResponseEntity<Void> save(@Valid @RequestBody Doctor doctor) {

        Doctor obj = doctorService.save(doctor);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        System.err.println(uri);
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping(value = "/logoff")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Recurso criado com sucesso. Médico salvo no banco de dados."),
            @ApiResponse(code = 400, message = "Pedido ruim. O servidor não pode ou não irá processar a requisição devido a alguma coisa que foi entendida como um erro."),
            @ApiResponse(code = 401, message = "Não autorizado."),
            @ApiResponse(code = 403, message = "Proibido. Você não tem permissão para acessar esse recurso."),
            @ApiResponse(code = 404, message = "O recurso não foi encontrado."),
            @ApiResponse(code = 422, message = "Entidade não processável."),
            @ApiResponse(code = 500, message = "O servidor encontrou uma condição inesperada e que o impediu de atender à solicitação.")
    })
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
    @ApiOperation(value = "Consultar todos os médicos armazenados no banco de dados.", response = Doctor.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Lista de médicos recuperada com sucesso."),
            @ApiResponse(code = 401, message = "Não autorizado."),
            @ApiResponse(code = 403, message = "Proibido. Você não tem permissão para acessar esse recurso."),
            @ApiResponse(code = 404, message = "O recurso não foi encontrado."),
            @ApiResponse(code = 500, message = "O servidor encontrou uma condição inesperada e que o impediu de atender à solicitação.")
    })
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
