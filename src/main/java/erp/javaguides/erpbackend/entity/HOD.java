package erp.javaguides.erpbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="HOD")

public class HOD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hodId;

    @Column(length = 20)
    private String discipline;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private String email;

    @Column(length = 15)
    private String mobileNumber;

    private List<String> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "hod",cascade = CascadeType.ALL)
    private List<Faculty> faculties;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "principal_id")
    private Principal principal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "officeBearer_id")
    private OfficeBearer officeBearer;

}
