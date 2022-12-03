package hu.vargyasb.universitydatabase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.vargyasb.universitydatabase.model.UniversityUser;

public interface UserRepository extends JpaRepository<UniversityUser, String> {

	Optional<UniversityUser> findByUsername(String username);
	Optional<UniversityUser> findByFacebookId(String fbId);
	Optional<UniversityUser> findByGoogleId(String googleId);
	
}
