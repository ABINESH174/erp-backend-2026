package erp.javaguides.erpbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Authentication")
public class Authentication {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,unique = true)
    private String userId;

    @Column(length = 255)
    private String password;

    @Column(length = 10)
    private String role;

    @Column(unique = true)
    private String email;

    private String forgotPasswordResetToken;

    private LocalDateTime forgotPasswordResetTokenExpiry;

    public Authentication(Long id, String userId, String password, String role) {
        this.id=id;
        this.userId=userId;
        this.password=password;
        this.role= role;
    }
}