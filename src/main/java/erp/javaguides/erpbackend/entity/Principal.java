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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Principal")
public class Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long principalId;

    @Column(length = 100)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    private String email;

    @Column(length = 15)
    private String mobileNumber;

    @Column(length = 12)
    private String aadharNumber;

    @OneToMany(mappedBy = "principal",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Hod> hods;

    public void addHod(Hod hod){
        hod.setPrincipal(this);
        if(hods == null){
            hods = new ArrayList<>();
        }
        this.hods.add(hod);
    }
    public void removeHod(Hod hod){
        this.hods.remove(hod);
        hod.setPrincipal(null);
    }

    public Principal(String firstName, String lastName, String email, String mobileNumber) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.mobileNumber=mobileNumber;
    }
}
