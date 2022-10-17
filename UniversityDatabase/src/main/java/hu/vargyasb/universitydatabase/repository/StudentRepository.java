package hu.vargyasb.universitydatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.vargyasb.universitydatabase.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
