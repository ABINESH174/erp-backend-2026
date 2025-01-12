package erp.javaguides.erpbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Id
    @Column(length = 50)
    private String email;
    @Column(length = 10)
    private String mobileNumber;
    @Column(length = 50)
    private String discipline;
    @Column(length = 50)
    private String handlingBatch;

    private List<String> subjects = new ArrayList<>();
    private List<String> handlingSemesters = new ArrayList<>();
    private List<String> handlingDepartments = new ArrayList<>();
    private List<String> batches = new ArrayList<>();
}