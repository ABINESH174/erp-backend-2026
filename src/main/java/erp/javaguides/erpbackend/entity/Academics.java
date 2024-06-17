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
    private String registerNo;
    private String programme;
    private String discipline;
    private String admissionNumber;
    private String academicYear;
    private String semester;
    private String abcId;
    private String umisId;
    private String dateOfAdmission;
    private String courseJoinedDate;
    private String courseType;
    private String regulation;
    private String fastTrack;
    private String cgpa;
    private String studentStatus;

}
