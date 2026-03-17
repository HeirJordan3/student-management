package com.student.student.student;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/students")
public class StudentController {
  private final StudentRepository repo;

  public StudentController(StudentRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Student> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public Student get(@PathVariable Long id) {
    return repo
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Student not found"));
  }

  @PostMapping
  public ResponseEntity<Student> create(@Valid @RequestBody StudentRequest req) {
    Student created = repo.save(new Student(req.firstName(), req.lastName(), req.email(), req.major()));
    return ResponseEntity.created(URI.create("/api/students/" + created.getId())).body(created);
  }

  @PutMapping("/{id}")
  public Student update(@PathVariable Long id, @Valid @RequestBody StudentRequest req) {
    Student existing =
        repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Student not found"));

    existing.setFirstName(req.firstName());
    existing.setLastName(req.lastName());
    existing.setEmail(req.email());
    existing.setMajor(req.major());
    return repo.save(existing);
  }

  @PatchMapping("/{id}")
  public Student patch(@PathVariable Long id, @Valid @RequestBody StudentPatchRequest req) {
    Student existing =
        repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Student not found"));

    if (req.firstName() != null) {
      if (req.firstName().isBlank()) {
        throw new ResponseStatusException(BAD_REQUEST, "firstName cannot be blank");
      }
      existing.setFirstName(req.firstName());
    }
    if (req.lastName() != null) {
      if (req.lastName().isBlank()) {
        throw new ResponseStatusException(BAD_REQUEST, "lastName cannot be blank");
      }
      existing.setLastName(req.lastName());
    }
    if (req.email() != null) {
      if (req.email().isBlank()) {
        throw new ResponseStatusException(BAD_REQUEST, "email cannot be blank");
      }
      existing.setEmail(req.email());
    }
    if (req.major() != null) {
      existing.setMajor(req.major());
    }

    return repo.save(existing);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!repo.existsById(id)) {
      throw new ResponseStatusException(NOT_FOUND, "Student not found");
    }
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

