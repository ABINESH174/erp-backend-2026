package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.CreateBonafideRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.enums.BonafideStatus;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.BonafideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonafideServiceImpl implements BonafideService {

    @Autowired
    private BonafideRepository bonafideRepository;
    @Autowired
    private StudentRepository studentRepository;
    private static final String FOLDERPATH = "C:\\Users\\Abinaya\\Desktop\\ERP Student Details";
    // @Autowired
    // private StudentServiceImpl studentService;

    @Override
    public BonafideResponseDto saveBonafide(CreateBonafideRequestDto requestDto) throws Exception {

        if (requestDto == null || requestDto.getRegisterNo() == null) {
            throw new IllegalArgumentException("StudentDto or Register Number cannot be null");
        }

        Student student = studentRepository.findByRegisterNo(requestDto.getRegisterNo())
                .orElseThrow(() -> new ResourceNotFoundException("The student is not found...!!!"));

        String userFolderPath = Paths.get(FOLDERPATH, requestDto.getRegisterNo()).toString();

        Bonafide bonafide = BonafideMapper.mapToBonafide(requestDto);

        // bonafide.setStudent(student);
        student.addBonafide(bonafide); // Add the bonafide to the student's list of bonafides

        Files.createDirectories(Paths.get(userFolderPath)); // Create the directory if it doesn't exist

        bonafide.setSmartCardFilePath(saveFile(requestDto.getSmartCardFile(), userFolderPath, "smartCard"));
        bonafide.setStudentIdCardFilePath(saveFile(requestDto.getStudentIdCardFile(), userFolderPath, "studentIdCard"));
        bonafide.setProvisionalAllotmentFilePath(
                saveFile(requestDto.getProvisionalAllotmentFile(), userFolderPath, "provisionalAllotment"));
        bonafide.setAadharCardFilePath(saveFile(requestDto.getAadharCardFile(), userFolderPath, "aadharCard"));
        bonafide.setCentralCommunityCertificateFilePath(saveFile(requestDto.getCentralCommunityCertificateFile(),
                userFolderPath, "centralCommunityCertificate"));
        bonafide.setCollegeFeeReceiptFilePath(
                saveFile(requestDto.getCollegeFeeReceiptFile(), userFolderPath, "collegeFeeReceipt"));
        bonafide.setWelfareIdFilePath(saveFile(requestDto.getLabourWelfareFile(), userFolderPath, "labourWelfareId"));

        // bonafide.setWelfareIdFilePath(studentService.saveFile(student.getFirstName(),
        // userFolderPath, "labourWelfareId",
        // studentService.base64ToMultipartFile(requestDto.getWelfareId(),
        // "labourWelfareId")));
        // bonafide.setSmartCardFilePath(studentService.saveFile(student.getFirstName(),
        // userFolderPath, "smartCard",
        // studentService.base64ToMultipartFile(requestDto.getSmartCard(),
        // "smartCard")));
        // bonafide.setStudentIdCardFilePath(studentService.saveFile(student.getFirstName(),
        // userFolderPath, "studentIdCard",
        // studentService.base64ToMultipartFile(requestDto.getStudentIdCard(),
        // "studentIdCard")));
        // bonafide.setProvisionalAllotmentFilePath(studentService.saveFile(student.getFirstName(),
        // userFolderPath, "provisionalAllotment",
        // studentService.base64ToMultipartFile(requestDto.getProvisionalAllotment(),
        // "provisionalAllotment")));
        // bonafide.setAadharCardFilePath(studentService.saveFile(student.getFirstName(),
        // userFolderPath, "aadharCard",
        // studentService.base64ToMultipartFile(requestDto.getAadharCard(),
        // "aadharCard")));
        // bonafide.setCentralCommunityCertificateFilePath(studentService.saveFile(student.getFirstName(),
        // userFolderPath, "centralCommunityCertificate",
        // studentService.base64ToMultipartFile(requestDto.getCentralCommunityCertificate(),
        // "centralCommunityCertificate")));
        // bonafide.setCollegeFeeReceiptFilePath(studentService.saveFile(student.getFirstName(),
        // userFolderPath, "collegeFeeReceipt",
        // studentService.base64ToMultipartFile(requestDto.getCollegeFeeReceipt(),
        // "collegeFeeReceipt")));

        Bonafide savedBonafide = bonafideRepository.save(bonafide);
        return BonafideMapper.mapToBonafideResponseDto(savedBonafide);
    }

    private String saveFile(MultipartFile file, String directory, String namePrefix) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = namePrefix + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path destination = Paths.get(directory, fileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return destination.toString(); // Store this path in the DB
        }
        return null;
    }

    @Override
    public BonafideResponseDto getBonafideById(Long bonafideId) {
        try {
            Bonafide bonafide = bonafideRepository.findById(bonafideId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with ID: " + bonafideId));
            return BonafideMapper.mapToBonafideResponseDto(bonafide);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Bonafide: " + e.getMessage(), e);
        }
    }

    @Override
    public BonafideResponseDto getBonafideByIdAndRegisterNo(Long bonafideId, String registerNo) {
        try {
            Optional<Bonafide> bonafideOptional = bonafideRepository.findByBonafideIdAndStudentRegisterNo(bonafideId, registerNo);
            Bonafide bonafide = bonafideOptional.orElseThrow(() -> new ResourceNotFoundException(
                    "Bonafide not found with ID: " + bonafideId + " and Register No: " + registerNo));
            return BonafideMapper.mapToBonafideResponseDto(bonafide);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Bonafide: " + e.getMessage(), e);
        }

    }

    @Override
    public List<BonafideResponseDto> getAllBonafidesByRegisterNo(String registerNo) {
        try {
            List<Bonafide> bonafides = bonafideRepository.findAllByStudentRegisterNo(registerNo);
            return bonafides.stream()
                    .map(BonafideMapper::mapToBonafideResponseDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Bonafides: " + e.getMessage(), e);
        }

    }

    @Override
    public List<BonafideResponseDto> getAllBonafidesByRegisterNoAndBonafideStatus(String registerNo, String status) {
        try {
            List<Bonafide> bonafides = bonafideRepository.findAllByStudentRegisterNo(registerNo);
            return bonafides.stream()
                    .filter(bonafide -> bonafide.getBonafideStatus().name().equalsIgnoreCase(status))
                    .map(BonafideMapper::mapToBonafideResponseDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Bonafides: " + e.getMessage(), e);
        }
    }


    @Override
    public List<BonafideResponseDto> getAllBonafides() {
        try {
            List<Bonafide> bonafides = bonafideRepository.findAll();
            return bonafides.stream()
                    .map(BonafideMapper::mapToBonafideResponseDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Bonafides: " + e.getMessage(), e);
        }
    }

    @Override
    public BonafideResponseDto updateBonafideWithBonafideStatus(Long bonafideId, String registerNo, String status) {
        try {
            Bonafide bonafide = bonafideRepository.findByBonafideIdAndStudentRegisterNo(bonafideId, registerNo)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Bonafide not found with ID: " + bonafideId + " and Register No: " + registerNo));
            bonafide.setBonafideStatus(BonafideStatus.valueOf(status.toUpperCase()));
            Bonafide updatedBonafide = bonafideRepository.save(bonafide);
            return BonafideMapper.mapToBonafideResponseDto(updatedBonafide);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Bonafide: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBonafide(Long bonafideId, String registerNo) {
        try {
            Bonafide bonafide = bonafideRepository.findByBonafideIdAndStudentRegisterNo(bonafideId, registerNo)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Bonafide not found with ID: " + bonafideId + " and Register No: " + registerNo));
            bonafideRepository.delete(bonafide);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Bonafide: " + e.getMessage(), e);
        }
    }

    //office bearer neccessities
    @Override
    public List<BonafideResponseDto> getHodApprovedBonafides() {
        return bonafideRepository.findByBonafideStatus(BonafideStatus.HOD_APPROVED)
                .stream()
                .map(BonafideMapper::mapToBonafideResponseDto)
                .toList();
    }

}

//
//
// @Override
// public List<BonafideDto> getAllBonafides() {
// List<Bonafide> bonafides = bonafideRepository.findAll();
// return bonafides.stream()
// .map(BonafideMapper::mapToBonafideDto)
// .collect(Collectors.toList());
// }
//
// @Override
// public BonafideDto updateBonafide(String registerNo, BonafideDto bonafideDto)
// {
// Bonafide existingBonafide = bonafideRepository.findById(registerNo)
// .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with
// Register No: " + registerNo));
//
// existingBonafide.setPurpose(bonafideDto.getPurpose());
// existingBonafide.setStatus(bonafideDto.getStatus());
//
// Bonafide updatedBonafide = bonafideRepository.save(existingBonafide);
// return BonafideMapper.mapToBonafideDto(updatedBonafide);
// }
//
// @Override
// public void deleteBonafide(String registerNo) {
// Bonafide bonafide = bonafideRepository.findById(registerNo)
// .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with
// Register No: " + registerNo));
// bonafideRepository.delete(bonafide);
// }
