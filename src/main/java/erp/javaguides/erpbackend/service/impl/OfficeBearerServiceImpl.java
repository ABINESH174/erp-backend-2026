package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.entity.Hod;
import erp.javaguides.erpbackend.entity.OfficeBearer;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.repository.HodRepository;
import erp.javaguides.erpbackend.repository.OfficeBearerRepository;
import erp.javaguides.erpbackend.utility.UtilityService;
import org.springframework.stereotype.Service;

import erp.javaguides.erpbackend.service.OfficeBearerService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfficeBearerServiceImpl implements OfficeBearerService{

    private final OfficeBearerRepository officeBearerRepository;

    private final HodRepository hodRepository;

    private final UtilityService utilityService;

    @Override
    public OfficeBearer saveOfficeBearer(OfficeBearer officeBearer) throws Exception {
        Optional<OfficeBearer> optionalOfficeBearer= officeBearerRepository.findByEmail(officeBearer.getEmail());
        if(optionalOfficeBearer.isPresent()){
            throw new Exception("Email already exists");
        }
        // Add Hods to the OfficeBearer
        List<Hod> hods = hodRepository.findAll();
        for (Hod hod : hods) {
            officeBearer.addHod(hod); // Add each Hod to the OfficeBearer
        }

        utilityService.addEmailToAuthentication(officeBearer.getEmail(),officeBearer.getEmail());

        return officeBearerRepository.save(officeBearer);
    }

    @Override
    public OfficeBearer getOfficeBearerByEmail(String email){
        OfficeBearer officeBearer;
        officeBearer = officeBearerRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("office bearer not found with email:"+email));
        return officeBearer;
    }


    
}
