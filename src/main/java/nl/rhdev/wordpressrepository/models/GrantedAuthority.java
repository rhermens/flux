package nl.rhdev.wordpressrepository.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authorities")
@Data
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {

    @Embeddable
    public static class GrantedAuthorityId implements Serializable {
        @ManyToOne(optional = false)
        @JoinColumn(name = "username")
        private User user;

        @Getter
        @Setter
        @Column(name = "authority")
        private String authority;
    }

    @EmbeddedId
    private GrantedAuthorityId id;

    public String getAuthority() {
        return id.getAuthority();
    }
}
