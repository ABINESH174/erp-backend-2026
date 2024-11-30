package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.BonafideDto;
import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.BonafideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BonafideServiceImpl implements BonafideService {

    @Autowired
    private BonafideRepository bonafideRepository;
    private StudentRepository studentRepository;
    private static final String FOLDERPATH = "C:\\Users\\m.uvasri\\Desktop\\FileSystem";
    private StudentServiceImpl studentService;

    @Override
    public BonafideDto createBonafide(BonafideDto bonafideDto) throws Exception{

        if (bonafideDto == null || bonafideDto.getRegisterNo() == null) {
            throw new IllegalArgumentException("StudentDto or Register Number cannot be null");
        }

        Student student = studentRepository.findById(bonafideDto.getRegisterNo())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Register Number: " + bonafideDto.getRegisterNo()));

        String userFolderPath = Paths.get(FOLDERPATH, bonafideDto.getRegisterNo()).toString();

        Bonafide bonafide = BonafideMapper.mapToBonafide(bonafideDto);

        bonafide.setLabourWelfareId(studentService.saveFile(student.getFirstName(), userFolderPath, "labourWelfareId",
                studentService.base64ToMultipartFile(bonafideDto.getLabourWelfareId(), "labourWelfareId")));
        bonafide.setSmartCard(studentService.saveFile(student.getFirstName(), userFolderPath, "smartCard",
                studentService.base64ToMultipartFile(bonafideDto.getSmartCard(), "smartCard")));
        bonafide.setStudentIdCard(studentService.saveFile(student.getFirstName(), userFolderPath, "studentIdCard",
                studentService.base64ToMultipartFile(bonafideDto.getStudentIdCard(), "studentIdCard")));
        bonafide.setProvisionalAllotment(studentService.saveFile(student.getFirstName(), userFolderPath, "provisionalAllotment",
                studentService.base64ToMultipartFile(bonafideDto.getProvisionalAllotment(), "provisionalAllotment")));
        bonafide.setAadharCard(studentService.saveFile(student.getFirstName(), userFolderPath, "aadharCard",
                studentService.base64ToMultipartFile(bonafideDto.getAadharCard(), "aadharCard")));
        bonafide.setCentralCommunityCertificate(studentService.saveFile(student.getFirstName(), userFolderPath, "centralCommunityCertificate",
                studentService.base64ToMultipartFile(bonafideDto.getCentralCommunityCertificate(), "centralCommunityCertificate")));
        bonafide.setCollegeFeeReceipt(studentService.saveFile(student.getFirstName(), userFolderPath, "collegeFeeReceipt",
                studentService.base64ToMultipartFile(bonafideDto.getCollegeFeeReceipt(), "collegeFeeReceipt")));

        Bonafide savedBonafide = bonafideRepository.save(bonafide);
        return BonafideMapper.mapToBonafideDto(savedBonafide);
    }

    @Override
    public BonafideDto getBonafideByRegisterNo(String registerNo) {
        Bonafide bonafide = bonafideRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));
        return BonafideMapper. mapToBonafideDto(bonafide);
    }

    @Override
    public List<BonafideDto> getAllBonafides() {
        List<Bonafide> bonafides = bonafideRepository.findAll();
        return bonafides.stream()
                .map(BonafideMapper::mapToBonafideDto)
                .collect(Collectors.toList());
    }

    @Override
    public BonafideDto updateBonafide(String registerNo, BonafideDto bonafideDto) {
        Bonafide existingBonafide = bonafideRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));

        existingBonafide.setPurpose(bonafideDto.getPurpose());
        existingBonafide.setStatus(bonafideDto.getStatus());

        Bonafide updatedBonafide = bonafideRepository.save(existingBonafide);
        return BonafideMapper.mapToBonafideDto(updatedBonafide);
    }

    @Override
    public void deleteBonafide(String registerNo) {
        Bonafide bonafide = bonafideRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));
        bonafideRepository.delete(bonafide);
    }
}

