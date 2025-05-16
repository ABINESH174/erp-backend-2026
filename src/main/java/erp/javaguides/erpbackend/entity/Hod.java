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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "principal_id")
    private Principal principal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "officeBearer_id")
    private OfficeBearer officeBearer;

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

}
