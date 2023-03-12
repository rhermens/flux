package nl.rhdev.wordpressrepository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.rhdev.wordpressrepository.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
