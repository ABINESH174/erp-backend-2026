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
@Table(name="Faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyId;

    private String firstName;

    private String lastName;

    private String email;

    @Column(length = 15)
    private String mobileNumber;

    @Column(length = 50)
    private String discipline;

    @Column(length = 50)
    private String handlingBatch;

    private List<String> subjects = new ArrayList<>();
    private List<String> handlingSemesters = new ArrayList<>();
    private List<String> handlingDepartments = new ArrayList<>();
    private List<String> batches = new ArrayList<>();

    @OneToMany(mappedBy = "faculty",cascade = CascadeType.ALL)
    private List<Student> students;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hod_id")
    private HOD hod;


    public Faculty(String firstName, String lastName, String email, String mobileNumber, String discipline, String handlingBatch, List<String> subjects, List<String> handlingSemesters, List<String> handlingDepartments, List<String> batches) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.discipline = discipline;
        this.handlingBatch = handlingBatch;
        this.subjects = subjects;
        this.handlingSemesters = handlingSemesters;
        this.handlingDepartments = handlingDepartments;
        this.batches = batches;
    }
}