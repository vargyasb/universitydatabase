package hu.vargyasb.universitydatabase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.vargyasb.universitydatabase.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

	Optional<Student> findByExternalId(int externalStudentId);

}
