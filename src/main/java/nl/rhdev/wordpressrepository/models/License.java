package nl.rhdev.wordpressrepository.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "licenses")
@Data
public class License {

    @Id
    @Column(name = "license_value")
    public String value;
}
