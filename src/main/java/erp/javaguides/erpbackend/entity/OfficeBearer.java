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
@Table(name = "Office_Bearer")
public class OfficeBearer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long obId;

    private String name;

    private String email;

    @OneToMany(mappedBy = "officeBearer", cascade = CascadeType.ALL)
    private List<Hod> hods;

    void addHod(Hod hod){
        if(hods.isEmpty()){
            hods = new ArrayList<>();
        }
        this.hods.add(hod);
        hod.setOfficeBearer(this);
    }

    public void removeHod(Hod hod) {
        this.hods.remove(hod);
        hod.setOfficeBearer(null);
    }

}
