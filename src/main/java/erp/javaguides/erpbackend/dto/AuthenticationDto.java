package erp.javaguides.erpbackend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Authentication")
public class AuthenticationDto {
    private Long id;
    private String userId;
    private String password;
    private String role;
}