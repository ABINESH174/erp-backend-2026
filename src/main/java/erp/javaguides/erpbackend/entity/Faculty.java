package erp.javaguides.erpbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @JsonManagedReference    // to avoid infinite recursion
    private List<Student> students;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hod_id")
    private Hod hod;

    // very important to create a relationship between both the entities
    // this method ensures that the student object is aware of its faculty using the faculty_id column update

    public void addStudent(Student student) {
        student.setFaculty(this);
        this.students.add(student);
    }


    public void removeStudent(Student student) {
        student.setFaculty(null);
        this.students.remove(student);
    }

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