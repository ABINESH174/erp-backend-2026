package erp.javaguides.erpbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import erp.javaguides.erpbackend.service.OfficeBearerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/office-bearer")
public class OfficeBearerController {
    
    @Autowired
    private OfficeBearerService officeBearerService;
    

}
