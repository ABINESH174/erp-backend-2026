package erp.javaguides.erpbackend.entity;

import erp.javaguides.erpbackend.enums.AuthenticationStatus;
import erp.javaguides.erpbackend.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Authentication")
public class Authentication implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Role role;

    @Column(unique = true)
    private String email;

    @Column(length = 10)
    private String forgotPasswordResetToken;

    private LocalDateTime forgotPasswordResetTokenExpiry;

    private boolean firstTimePasswordResetFlag;

    @Enumerated(EnumType.STRING)
    private AuthenticationStatus authenticationStatus;

    public Authentication(Long id, String userId, String password, Role role) {
        this.id=id;
        this.userId=userId;
        this.password=password;
        this.role= role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        if(this.role.name().equals("STUDENT")) {
            return this.userId;
        }
        return this.email;
    }

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
        return true;
    }

    public boolean isUserActive() {
        return this.authenticationStatus.equals(AuthenticationStatus.ACTIVE);
    }
}