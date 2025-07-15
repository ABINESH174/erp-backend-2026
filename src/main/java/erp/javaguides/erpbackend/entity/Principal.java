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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Principal")
public class Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long principalId;

    private String firstName;

    private String lastName;

    private String email;

    @Column(length = 15)
    private String mobileNumber;

    @OneToMany(mappedBy = "principal",cascade = CascadeType.ALL)
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
