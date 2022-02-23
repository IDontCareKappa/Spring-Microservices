package pl.tostrowski.students.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.tostrowski.students.exception.StudentError;
import pl.tostrowski.students.exception.StudentException;
import pl.tostrowski.students.model.Student;
import pl.tostrowski.students.repository.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        studentRepository.delete(student);
    }

    @Override
    public Student putStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentToUpdate -> {
                    studentToUpdate.setFirstName(student.getFirstName());
                    studentToUpdate.setLastName(student.getLastName());
                    studentToUpdate.setEmail(student.getEmail());
                    return studentRepository.save(studentToUpdate);
                })
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public Student patchStudent(Long id, Student student) {
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
                    return studentRepository.save(studentToUpdate);
                })
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }


}
