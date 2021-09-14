package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Paciente;
import br.com.conexasaude.models.views.PacienteVisao;
import br.com.conexasaude.services.PacienteService;
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
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @GetMapping(value = "/patients/{id}")
    @JsonView(PacienteVisao.PacienteCompleto.class)
    public ResponseEntity<Paciente> findById(@PathVariable Long id) {

        Paciente paciente = pacienteService.getById(id);

        return ResponseEntity.ok().body(paciente);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @GetMapping(value = "/patients")
    @JsonView(PacienteVisao.PacienteCompleto.class)
    public ResponseEntity<List<Paciente>> findAll() {

        List<Paciente> pacientes = pacienteService.getAll();

        return ResponseEntity.ok(pacientes);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PostMapping(value = "/patients")
    @JsonView(PacienteVisao.PacienteCompleto.class)
    public ResponseEntity<Void> save(@Valid @RequestBody Paciente paciente) {

        Paciente obj = pacienteService.save(paciente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PutMapping(value = "/patients/{id}")
    @JsonView(PacienteVisao.PacienteUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Paciente paciente, @PathVariable Long id) {

        paciente.setId(id);
        pacienteService.update(paciente);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @DeleteMapping(value = "/patients/{id}")
    @JsonView(PacienteVisao.PacienteSimples.class)
    public ResponseEntity<Void> delete(@PathVariable Long id){

        pacienteService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
