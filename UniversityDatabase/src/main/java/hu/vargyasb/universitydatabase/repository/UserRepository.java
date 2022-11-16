package hu.vargyasb.universitydatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.vargyasb.universitydatabase.model.UniversityUser;

public interface UserRepository extends JpaRepository<UniversityUser, String> {

}
