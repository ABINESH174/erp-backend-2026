package erp.javaguides.erpbackend.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String Email_Id;
    @Column(name="Register_No",nullable=false,unique=true)
    private String Register_No;
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
