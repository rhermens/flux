package nl.rhdev.wordpressrepository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.rhdev.wordpressrepository.models.License;

@Repository
public interface LicenseRepository extends JpaRepository<License, String> {
}
