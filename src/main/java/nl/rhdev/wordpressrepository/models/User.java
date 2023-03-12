package nl.rhdev.wordpressrepository.models;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    private Boolean enabled;

    @Getter
    @Setter
    @OneToMany(mappedBy = "id.user", fetch = FetchType.EAGER)
    private Set<GrantedAuthority> authorities;

	@Override
	public boolean isAccountNonExpired() {
        return true;
	}

	@Override
	public boolean isAccountNonLocked() {
        return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
        return true;
	}

	@Override
	public boolean isEnabled() {
        return enabled;
	}
}
