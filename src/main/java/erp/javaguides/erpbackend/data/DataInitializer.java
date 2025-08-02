package erp.javaguides.erpbackend.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
import erp.javaguides.erpbackend.dto.requestDto.HodRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;
import erp.javaguides.erpbackend.entity.OfficeBearer;
import erp.javaguides.erpbackend.entity.Principal;
import erp.javaguides.erpbackend.repository.PrincipalRepository;
import erp.javaguides.erpbackend.service.AuthenticationService;
import erp.javaguides.erpbackend.service.HodService;
import erp.javaguides.erpbackend.service.OfficeBearerService;
import erp.javaguides.erpbackend.service.PrincipalService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent>{

    private String principalEmail="91762215003@accet.ac.in";

    private final AuthenticationService authenticationService;
    private final PrincipalService principalService;
    private final HodService hodService;
    private final OfficeBearerService officeBearerService;

    private final PrincipalRepository principalRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//         createPrincipal();
//         createHods();
//         try {
//             createOfficeBearer();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         authenticateStudentAndFaculty();
    }

    // Implement your data initialization logic here
    private void createAuthentication(String userId, String password, String role){
        AuthenticationDto authenticationDto = authenticationService.createAuthentication(new AuthenticationDto(userId, password, role));
    }

    private void createPrincipal(){
        if (principalRepository.existsByEmail(principalEmail)) {
            return;
        }
        Principal principal = new Principal("Principal","Acgcet",this.principalEmail,"8787878787");
        createAuthentication(principal.getEmail(),"123456" , "PRINCIPAL");

        Principal createdPrincipal = principalService.savePrincipal(principal);
    }

    private void createHods() {
        if (hodService.getAllHods().isEmpty()){
            HodRequestDto hodCse = new HodRequestDto("Hodcse","Cse","duraiyan100@gmail.com","7878878788","Computer Science and Engineering", "Computer Science and Engineering", this.principalEmail );
            createAuthentication(hodCse.getEmail(), "123456", "HOD");
            HodResponseDto createdHodCse = hodService.createHod(hodCse);


            HodRequestDto hodEce = new HodRequestDto("HodEce", "ece", "ecehod@gmail.com","9898989890","Electronics and Communication Engineering", "Electronics and Communication Engineering", this.principalEmail);
            createAuthentication(hodEce.getEmail(), "123456", "HOD");
            HodResponseDto createdHodEce = hodService.createHod(hodEce);


            HodRequestDto cfa = new HodRequestDto("CFA","cfa", "dhanushdhanush3732@gmail.com", "9696969696", "Science and Humanities", "Science and Humanities", this.principalEmail);
            createAuthentication(cfa.getEmail(), "123456", "HOD");
            HodResponseDto createdCfa = hodService.createHod(cfa);

        }
    }

    private void createOfficeBearer() throws Exception {
        OfficeBearer bonafideOb = new OfficeBearer("BonafideOB","abineshsubramanian8@gmail.com","Bonafide");
        createAuthentication(bonafideOb.getEmail(), "123456", "OB");
        OfficeBearer createdOb = officeBearerService.saveOfficeBearer(bonafideOb);

    }

    private void authenticateStudentAndFaculty() {
        createAuthentication("studentcse1", "studentcse1", "STUDENT");
        createAuthentication("killervenom4002@gmail.com", "facultycse1", "FA");
        createAuthentication("firstYearCseStudent","123456","STUDENT");
        createAuthentication("dhanushajay2115@gmail.com", "123456", "FA");
    }
    
}
