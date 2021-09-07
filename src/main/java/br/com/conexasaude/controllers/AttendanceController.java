package br.com.conexasaude.controllers;

import br.com.conexasaude.models.Attendance;
import br.com.conexasaude.services.AttendanceService;
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
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping(value = "attendances/{id}")
    public ResponseEntity<Attendance> findById(@PathVariable Long id) {

        Attendance attendance = attendanceService.getById(id);

        return ResponseEntity.ok().body(attendance);
    }

    @GetMapping(value = "/attendances")
    public ResponseEntity<List<Attendance>> findAll() {

        List<Attendance> attendances = attendanceService.getAll();

        return ResponseEntity.ok().body(attendances);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping(value = "/attendances")
    public ResponseEntity<Void> save(@Valid @RequestBody Attendance attendance) {

        Attendance obj = attendanceService.save(attendance);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    // @PreAuthorize("hasRole('DOCTOR')")
    @DeleteMapping(value = "/attendances/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){

        attendanceService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
