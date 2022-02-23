package pl.tostrowski.students.service;

import pl.tostrowski.students.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    Student getStudent(Long id);

    Student addStudent(Student student);

    void deleteStudent(Long id);

    Student putStudent(Long id, Student student);

    Student patchStudent(Long id, Student student);

}
