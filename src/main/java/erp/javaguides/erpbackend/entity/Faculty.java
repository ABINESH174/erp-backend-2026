package erp.javaguides.erpbackend.entity;

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
    private String firstName;
    private String lastName;
    @Id
    private String email;
    private String mobileNumber;
    private String discipline;
    private String handlingBatch;

    private List<String> subjects = new ArrayList<>();
    private List<String> handlingSemesters = new ArrayList<>();
    private List<String> handlingDepartments = new ArrayList<>();
    private List<String> batches = new ArrayList<>();
}