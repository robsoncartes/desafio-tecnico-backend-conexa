package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Atendimento;
import br.com.conexasaude.services.AtendimentoService;
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
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @GetMapping(value = "attendances/{id}")
    public ResponseEntity<Atendimento> findById(@PathVariable Long id) {

        Atendimento attendance = atendimentoService.getById(id);

        return ResponseEntity.ok().body(attendance);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @GetMapping(value = "/attendances")
    public ResponseEntity<List<Atendimento>> findAll() {

        List<Atendimento> attendances = atendimentoService.getAll();

        return ResponseEntity.ok().body(attendances);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PostMapping(value = "/attendances")
    public ResponseEntity<Void> save(@Valid @RequestBody Atendimento atendimento) {

        Atendimento obj = atendimentoService.save(atendimento);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @DeleteMapping(value = "/attendances/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        atendimentoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
