package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.entity.OfficeBearer;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.repository.OfficeBearerRepository;
import org.springframework.stereotype.Service;

import erp.javaguides.erpbackend.service.OfficeBearerService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfficeBearerServiceImpl implements OfficeBearerService{

    private final OfficeBearerRepository officeBearerRepository;

    @Override
    public OfficeBearer saveOfficeBearer(OfficeBearer officeBearer) throws Exception {
        Optional<OfficeBearer> optionalOfficeBearer= officeBearerRepository.findByEmail(officeBearer.getEmail());
        if(optionalOfficeBearer.isPresent()){
            throw new Exception("Email already exists");
        }
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
