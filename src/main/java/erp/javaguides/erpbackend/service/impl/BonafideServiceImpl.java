package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.BonafideDto;
import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.BonafideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class BonafideServiceImpl implements BonafideService {

    @Autowired
    private BonafideRepository bonafideRepository;
    @Autowired
    private StudentRepository studentRepository;
    private static final String FOLDERPATH = "C:\\Users\\Acer\\Documents\\TempERPData\\fileSystem";
    @Autowired
    private StudentServiceImpl studentService;

    @Override
    public BonafideDto createBonafide(BonafideDto bonafideDto) throws Exception{

        if (bonafideDto == null || bonafideDto.getRegisterNo() == null) {
            throw new IllegalArgumentException("StudentDto or Register Number cannot be null");
        }

        Student student = studentRepository.findByRegisterNo(bonafideDto.getRegisterNo())
                .orElseThrow(()->
                        new ResourceNotFoundException("The student is not found...!!!")
                );

        String userFolderPath = Paths.get(FOLDERPATH, bonafideDto.getRegisterNo()).toString();

        Bonafide bonafide = BonafideMapper.mapToBonafide(bonafideDto);

        bonafide.setWelfareIdFilePath(studentService.saveFile(student.getFirstName(), userFolderPath, "labourWelfareId",
                studentService.base64ToMultipartFile(bonafideDto.getWelfareId(), "labourWelfareId")));
        bonafide.setSmartCardFilePath(studentService.saveFile(student.getFirstName(), userFolderPath, "smartCard",
                studentService.base64ToMultipartFile(bonafideDto.getSmartCard(), "smartCard")));
        bonafide.setStudentIdCardFilePath(studentService.saveFile(student.getFirstName(), userFolderPath, "studentIdCard",
                studentService.base64ToMultipartFile(bonafideDto.getStudentIdCard(), "studentIdCard")));
        bonafide.setProvisionalAllotmentFilePath(studentService.saveFile(student.getFirstName(), userFolderPath, "provisionalAllotment",
                studentService.base64ToMultipartFile(bonafideDto.getProvisionalAllotment(), "provisionalAllotment")));
        bonafide.setAadharCardFilePath(studentService.saveFile(student.getFirstName(), userFolderPath, "aadharCard",
                studentService.base64ToMultipartFile(bonafideDto.getAadharCard(), "aadharCard")));
        bonafide.setCentralCommunityCertificateFilePath(studentService.saveFile(student.getFirstName(), userFolderPath, "centralCommunityCertificate",
                studentService.base64ToMultipartFile(bonafideDto.getCentralCommunityCertificate(), "centralCommunityCertificate")));
        bonafide.setCollegeFeeReceiptFilePath(studentService.saveFile(student.getFirstName(), userFolderPath, "collegeFeeReceipt",
                studentService.base64ToMultipartFile(bonafideDto.getCollegeFeeReceipt(), "collegeFeeReceipt")));

        Bonafide savedBonafide = bonafideRepository.save(bonafide);
        return BonafideMapper.mapToBonafideDto(savedBonafide);
    }

//    @Override
//    public BonafideDto getBonafideByRegisterNo(String registerNo) {
//        Bonafide bonafide = bonafideRepository.findById(registerNo)
//                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));
//        return BonafideMapper. mapToBonafideDto(bonafide);
//    }
//
//    @Override
//    public List<BonafideDto> getAllBonafides() {
//        List<Bonafide> bonafides = bonafideRepository.findAll();
//        return bonafides.stream()
//                .map(BonafideMapper::mapToBonafideDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public BonafideDto updateBonafide(String registerNo, BonafideDto bonafideDto) {
//        Bonafide existingBonafide = bonafideRepository.findById(registerNo)
//                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));
//
//        existingBonafide.setPurpose(bonafideDto.getPurpose());
//        existingBonafide.setStatus(bonafideDto.getStatus());
//
//        Bonafide updatedBonafide = bonafideRepository.save(existingBonafide);
//        return BonafideMapper.mapToBonafideDto(updatedBonafide);
//    }
//
//    @Override
//    public void deleteBonafide(String registerNo) {
//        Bonafide bonafide = bonafideRepository.findById(registerNo)
//                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));
//        bonafideRepository.delete(bonafide);
//    }
}

