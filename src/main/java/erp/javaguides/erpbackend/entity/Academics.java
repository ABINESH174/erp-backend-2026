package erp.javaguides.erpbackend.entity;

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
@Table(name="Academics")
public class Academics {
    @Id
//    private String emailid;
    @Column(unique = true)
    private String register_No;
    private String Programme;
    private String Discipline;
    private String Admission_Number;
    private String Academic_Year;
    private String Semester;
    private String ABC_Id;
    private String UMIS_Id;
    private String Date_Of_Admission;
    private String Course_Joined_Date;
    private String Course_Type;
    private String Regulation;
    private String Fast_Track;
    private String CGPA;
    private String Student_Status;
}
