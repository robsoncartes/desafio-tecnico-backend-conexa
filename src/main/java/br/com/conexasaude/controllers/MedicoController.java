package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Medico;
import br.com.conexasaude.models.views.MedicoVisao;
import br.com.conexasaude.security.JWTUtil;
import br.com.conexasaude.security.Login;
import br.com.conexasaude.services.LoginService;
import br.com.conexasaude.services.MedicoService;
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
public class MedicoController {

    private JWTUtil jwtUtil;
    private LoginService loginService;
    private MedicoService medicoService;


    @ApiOperation(value = "Obtenha um médico com base em seu ID", response = Medico.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Médico recuperado com sucesso."),
            @ApiResponse(code = 401, message = "Não autorizado."),
            @ApiResponse(code = 403, message = "Proibido. Você não tem permissão para acessar esse recurso."),
            @ApiResponse(code = 404, message = "O recurso não foi encontrado."),
            @ApiResponse(code = 500, message = "O servidor encontrou uma condição inesperada e que o impediu de atender à solicitação.")})
    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @GetMapping(value = "/doctors/{id}")
    @JsonView(MedicoVisao.MedicoCompleto.class)
    public ResponseEntity<Medico> findById(@PathVariable Long id) {

        Medico medico = medicoService.getById(id);

        return ResponseEntity.ok().body(medico);
    }

    @ApiOperation(value = "Salvar um novo médico no banco de dados", response = Medico.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Recurso criado com sucesso. Médico salvo no banco de dados."),
            @ApiResponse(code = 400, message = "Pedido ruim. O servidor não pode ou não irá processar a requisição devido a alguma coisa que foi entendida como um erro."),
            @ApiResponse(code = 401, message = "Não autorizado."),
            @ApiResponse(code = 403, message = "Proibido. Você não tem permissão para acessar esse recurso."),
            @ApiResponse(code = 404, message = "O recurso não foi encontrado."),
            @ApiResponse(code = 422, message = "Entidade não processável."),
            @ApiResponse(code = 500, message = "O servidor encontrou uma condição inesperada e que o impediu de atender à solicitação.")})
    @PostMapping(value = "/signup")
    public ResponseEntity<Void> save(@Valid @RequestBody Medico medico) {

        Medico obj = medicoService.save(medico);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @ApiResponses(value = {@ApiResponse(code = 201, message = "Recurso criado com sucesso. Médico salvo no banco de dados."),
            @ApiResponse(code = 400, message = "Pedido ruim. O servidor não pode ou não irá processar a requisição devido a alguma coisa que foi entendida como um erro."),
            @ApiResponse(code = 401, message = "Não autorizado."),
            @ApiResponse(code = 403, message = "Proibido. Você não tem permissão para acessar esse recurso."),
            @ApiResponse(code = 404, message = "O recurso não foi encontrado."),
            @ApiResponse(code = 422, message = "Entidade não processável."),
            @ApiResponse(code = 500, message = "O servidor encontrou uma condição inesperada e que o impediu de atender à solicitação.")})
    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PostMapping(value = "/logoff")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {

        Login login = loginService.getAuthenticated();
        String token = jwtUtil.generateToken(login.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Consultar todos os médicos armazenados no banco de dados.", response = Medico.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Lista de médicos recuperada com sucesso."),
            @ApiResponse(code = 401, message = "Não autorizado."),
            @ApiResponse(code = 403, message = "Proibido. Você não tem permissão para acessar esse recurso."),
            @ApiResponse(code = 404, message = "O recurso não foi encontrado."),
            @ApiResponse(code = 500, message = "O servidor encontrou uma condição inesperada e que o impediu de atender à solicitação.")})
    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @GetMapping(value = "/doctors")
    @JsonView(MedicoVisao.MedicoCompleto.class)
    public ResponseEntity<List<Medico>> findAll() {

        List<Medico> medicos = medicoService.getAll();

        return ResponseEntity.ok(medicos);
    }
}
