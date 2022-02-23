package pl.tostrowski.students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pl.tostrowski.students.repository.StudentRepository;
import pl.tostrowski.students.model.Student;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody @Valid Student student, @PathVariable Long id){
        return studentRepository.findById(id)
                .map(studentToUpdate -> {
                    studentToUpdate.setFirstName(student.getFirstName());
                    studentToUpdate.setLastName(student.getLastName());
                    studentToUpdate.setEmail(student.getEmail());
                    return ResponseEntity.ok().body(studentRepository.save(studentToUpdate));
                })
                .orElseGet(() -> {return ResponseEntity.notFound().build();});
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@RequestBody Student student, @PathVariable Long id){
        return studentRepository.findById(id)
                .map(studentToUpdate -> {
                    if (!StringUtils.isEmpty(student.getFirstName())){
                        studentToUpdate.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())){
                        studentToUpdate.setFirstName(student.getLastName());
                    }

                    studentToUpdate.setLastName(student.getLastName());
                    studentToUpdate.setEmail(student.getEmail());
                    return ResponseEntity.ok().body(studentRepository.save(studentToUpdate));
                })
                .orElseGet(() -> {return ResponseEntity.notFound().build();});
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> {return ResponseEntity.notFound().build();});

    }

}
