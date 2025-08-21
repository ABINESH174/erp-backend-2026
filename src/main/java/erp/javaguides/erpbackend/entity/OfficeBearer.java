package erp.javaguides.erpbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Office_Bearer")
public class OfficeBearer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long obId;

    private String name;

    private String email;

    private String handlingPurpose;

    @Column(length = 10)
    private String mobileNumber;

    @Column(length = 12)
    private String aadharNumber;

    // Many-to-many relationship with HOD
    // @JoinTable defines the join table and its columns
    // This is the owning side of the relationship (the one that manages the join table)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Cascade PERSIST and MERGE operations
    @JoinTable(
        name = "hod_ob_mapping", // Name of the join table
        joinColumns = @JoinColumn(name = "ob_id"), // Column in join table referring to OB (this entity)
        inverseJoinColumns = @JoinColumn(name = "hod_id") // Column in join table referring to HOD (the other entity)
    )
    private Set<Hod> hods = new HashSet<>();

    //Helper method to add a HOD to the OfficeBearer
    public void addHod(Hod hod) {
        this.hods.add(hod);
        hod.getOfficeBearers().add(this); // Ensure bidirectional relationship
    }

    //Helper method to remove a HOD from the OfficeBearer
    public void removeHod(Hod hod) {
        this.hods.remove(hod);
        hod.getOfficeBearers().remove(this); // Ensure bidirectional relationship
    }

    public OfficeBearer(String name, String email, String handlingPurpose) {
        this.name=name;
        this.email=email;
        this.handlingPurpose=handlingPurpose;
    }
}
