package erp.javaguides.erpbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="HOD")

public class Hod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hodId;

    @Column(length = 100)
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

    @ManyToOne
    @JoinColumn(name = "principal_id")
    private Principal principal;

    @ManyToMany(mappedBy = "hods")
    private Set<OfficeBearer> officeBearers = new HashSet<>();

    public void addFaculty(Faculty faculty) {
        if(faculties.isEmpty()){
            faculties = new ArrayList<>();
        }
        this.faculties.add(faculty);
        faculty.setHod(this);
    }

    public void removeFaculty(Faculty faculty) {
        this.faculties.remove(faculty);
        faculty.setHod(null);
    }

    // public void addPrincipal(Principal principal) {
    //     this.principal = principal;
    //     principal.addHod(this);
    // }

    // public void removePrincipal(Principal principal) {
    //     this.principal = null;
    //     principal.removeHod(this);
    // }

}
