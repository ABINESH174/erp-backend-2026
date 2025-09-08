package erp.javaguides.erpbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String discipline;  // There are six disciplines in the application including 'Science and Humanities' for first year ( CFA ), didn't create a separate role for CFA since its similar to HOD

    @Column(length = 50)
    private String department;    // The department is now as same as the discipline in the application, but if the CFA is from a different dept (like maths, physics, chemistry, english ...), it will be for future integration.

    @Column(length = 100, nullable = false)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    private String email;

    @Column(length = 10)
    private String mobileNumber;

    @Column(length = 12)
    private String aadharNumber;

    private List<String> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "hod",cascade = CascadeType.ALL)
    private List<Faculty> faculties;

    @ManyToOne
    @JoinColumn(name = "principal_id")
    @JsonIgnore
    private Principal principal;

    @ManyToMany(mappedBy = "hods")
    @JsonIgnore
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

    public void addAllOfficeBearers(List<OfficeBearer> officeBearers) {
        for(OfficeBearer officeBearer : officeBearers) {
            officeBearer.addHod(this);
        }
    }

}
