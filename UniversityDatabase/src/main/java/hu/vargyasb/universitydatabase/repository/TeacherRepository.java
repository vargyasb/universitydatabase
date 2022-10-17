package hu.vargyasb.universitydatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.vargyasb.universitydatabase.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

}
