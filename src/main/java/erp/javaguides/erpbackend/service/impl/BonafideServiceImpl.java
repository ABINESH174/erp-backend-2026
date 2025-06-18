package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.CreateBonafideRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.ApplicableBonafideResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.enums.BonafideStatus;
import erp.javaguides.erpbackend.enums.Gender;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.BonafideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Struct;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BonafideServiceImpl implements BonafideService {

    @Autowired
    private BonafideRepository bonafideRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Value("${bonafide.details.base-path}")
    private String FOLDERPATH;
    // @Autowired
    // private StudentServiceImpl studentService;

    @Override
    public BonafideResponseDto saveBonafide(CreateBonafideRequestDto requestDto) throws Exception {

        if (requestDto == null || requestDto.getRegisterNo() == null) {
            throw new IllegalArgumentException("StudentDto or Register Number cannot be null");
        }

        // System.out.println("\n\nRegister No: " + requestDto.getRegisterNo());

        Student student = studentRepository.findByRegisterNo(requestDto.getRegisterNo())
                .orElseThrow(() -> new ResourceNotFoundException("The student is not found...!!!"));

        // System.out.println("\n\nStudent: " + student.getFirstName() + " with Register No: " + student.getRegisterNo());

        Bonafide bonafide = BonafideMapper.mapToBonafide(requestDto);

        // bonafide.setStudent(student);
        student.addBonafide(bonafide); // Add the bonafide to the student's list of bonafides

        Bonafide savedBonafide = bonafideRepository.save(bonafide);

        // Folder Path with structure: "/basepath/registerNo/bonafideId"
        String userFolderPath = Paths.get(FOLDERPATH, requestDto.getRegisterNo(), savedBonafide.getBonafideId().toString()).toString();

        Files.createDirectories(Paths.get(userFolderPath)); // Create the directory if it doesn't exist

        savedBonafide.setSmartCardFilePath(saveFile(requestDto.getSmartCardFile(), userFolderPath, "smartCard"));
        savedBonafide.setStudentIdCardFilePath(saveFile(requestDto.getStudentIdCardFile(), userFolderPath, "studentIdCard"));
        savedBonafide.setProvisionalAllotmentFilePath(
                saveFile(requestDto.getProvisionalAllotmentFile(), userFolderPath, "provisionalAllotment"));
        savedBonafide.setAadharCardFilePath(saveFile(requestDto.getAadharCardFile(), userFolderPath, "aadharCard"));
        savedBonafide.setCentralCommunityCertificateFilePath(saveFile(requestDto.getCentralCommunityCertificateFile(),
                userFolderPath, "centralCommunityCertificate"));
        savedBonafide.setCollegeFeeReceiptFilePath(
                saveFile(requestDto.getCollegeFeeReceiptFile(), userFolderPath, "collegeFeeReceipt"));
        savedBonafide.setWelfareIdFilePath(saveFile(requestDto.getLabourWelfareFile(), userFolderPath, "labourWelfareId"));

        return BonafideMapper.mapToBonafideResponseDto(bonafideRepository.save(savedBonafide));
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
    public BonafideResponseDto updateObRejectedBonafide(Long bonafideId, String registerNo, String rejectionMessage) {
        try {
            Bonafide bonafide = bonafideRepository.findByBonafideIdAndStudentRegisterNo(bonafideId, registerNo)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Bonafide not found with ID: " + bonafideId + " and Register No: " + registerNo));
            bonafide.setBonafideStatus(BonafideStatus.OB_REJECTED);
            bonafide.setRejectionMessage(rejectionMessage);

            // Deleting the files associated with the Bonafide
            if (bonafide.getSmartCardFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getSmartCardFilePath()));
                bonafide.setSmartCardFilePath(null); // Clear the path after deletion
            }
            if (bonafide.getStudentIdCardFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getStudentIdCardFilePath()));
                bonafide.setStudentIdCardFilePath(null);
            }
            if (bonafide.getProvisionalAllotmentFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getProvisionalAllotmentFilePath()));
                bonafide.setProvisionalAllotmentFilePath(null);
            }
            if (bonafide.getAadharCardFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getAadharCardFilePath()));
                bonafide.setAadharCardFilePath(null);
            }
            if (bonafide.getCentralCommunityCertificateFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getCentralCommunityCertificateFilePath()));
                bonafide.setCentralCommunityCertificateFilePath(null);
            }
            if (bonafide.getCollegeFeeReceiptFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getCollegeFeeReceiptFilePath()));
                bonafide.setCollegeFeeReceiptFilePath(null);
            }
            if (bonafide.getWelfareIdFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getWelfareIdFilePath()));
                bonafide.setWelfareIdFilePath(null);
            }

            return BonafideMapper.mapToBonafideResponseDto(bonafideRepository.save(bonafide));
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

            // Deleting the files associated with the Bonafide
            if (bonafide.getSmartCardFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getSmartCardFilePath()));
            }
            if (bonafide.getStudentIdCardFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getStudentIdCardFilePath()));
            }
            if (bonafide.getProvisionalAllotmentFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getProvisionalAllotmentFilePath()));
            }
            if (bonafide.getAadharCardFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getAadharCardFilePath()));
            }
            if (bonafide.getCentralCommunityCertificateFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getCentralCommunityCertificateFilePath()));
            }
            if (bonafide.getCollegeFeeReceiptFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getCollegeFeeReceiptFilePath()));
            }
            if (bonafide.getWelfareIdFilePath() != null) {
                Files.deleteIfExists(Paths.get(bonafide.getWelfareIdFilePath()));
            }
            bonafideRepository.delete(bonafide);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Bonafide: " + e.getMessage(), e);
        }
    }

    @Override
    public ApplicableBonafideResponseDto getApplicableBonafied(String registerNo) {
        Student student = studentRepository.findByRegisterNo(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("The student is not found...!!!"));

        ApplicableBonafideResponseDto purposeCheck = new ApplicableBonafideResponseDto();

        List<Bonafide> bonafides = bonafideRepository.findAllByStudentRegisterNo(registerNo);

        purposeCheck.setApplyBusPass(true);
        purposeCheck.setApplyPassport(true);
        purposeCheck.setApplyEducationSupport(true);
        purposeCheck.setApplyInternship(true);

        purposeCheck.setLabourWelfareScholarship(true);
        purposeCheck.setTailorWelfareScholarship(true);
        purposeCheck.setFarmerWelfareScholarship(true);

        for (Bonafide bonafide : bonafides) {
            String purpose = bonafide.getPurpose();

            switch (purpose) {
                case "bonafide for bc/mbc/dnc post metric scholarship":
                    purposeCheck.setBcMbcDncPostMetricScholarship(false);
                    break;
                case "bonafide for sc/st post metric scholorship":
                    purposeCheck.setScStScaPostMetricScholarship(false);
                    break;
                case "bonafide for tamilpudhalvan scholorship":
                    purposeCheck.setTamilPudhalvanScholarship(false);
                    break;
                case "bonafide for pudhumaipenn scholorship":
                    purposeCheck.setPudhumaiPennScholarship(false);
                    break;
                case "bonafide for labourwelfare", "bonafide for tailorwelfare", "bonafide for farmerwelfare":
                    purposeCheck.setLabourWelfareScholarship(false);
                    purposeCheck.setTailorWelfareScholarship(false);
                    purposeCheck.setFarmerWelfareScholarship(false);
                    break;
                default:
                    break;
            }
        }

        // Validate constraints directly by accessing DTO fields
        // Constraint for BC MBC DNS
        if (Boolean.TRUE.equals(purposeCheck.getBcMbcDncPostMetricScholarship())) {
            String income = student.getIncome();
            String caste = student.getCaste();

            boolean isCasteEligible = caste != null && (
                    caste.equalsIgnoreCase("bc") ||
                            caste.equalsIgnoreCase("mbc") ||
                            caste.equalsIgnoreCase("dns")
            );

            boolean isIncomeEligible = income != null && income.compareTo("250000") <= 0;

            purposeCheck.setBcMbcDncPostMetricScholarship(isCasteEligible && isIncomeEligible);
        }
        // Constraint for SC ST
        if (Boolean.TRUE.equals(purposeCheck.getScStScaPostMetricScholarship())) {
            String income = student.getIncome();
            String caste = student.getCaste();

            boolean isCasteEligible = caste != null && (
                    caste.equalsIgnoreCase("sc") ||
                            caste.equalsIgnoreCase("st")
            );

            boolean isIncomeEligible = income != null && income.compareTo("250000") <= 0;

            purposeCheck.setScStScaPostMetricScholarship(isCasteEligible && isIncomeEligible);
        }

        // Constraint for TAMILPUDHALVAN
        if (Boolean.TRUE.equals(purposeCheck.getTamilPudhalvanScholarship())) {
            Boolean isGovtSchool = student.getIsGovtSchool(); // Boolean field
            Gender gender = student.getGender();

            purposeCheck.setTamilPudhalvanScholarship(Boolean.TRUE.equals(isGovtSchool) && Gender.MALE.equals(gender));
        }

        // Constraint for PUDHUMAIPENN
        if (Boolean.TRUE.equals(purposeCheck.getPudhumaiPennScholarship())) {
            Boolean isGovtSchool = student.getIsGovtSchool();
            Gender gender = student.getGender();

            purposeCheck.setPudhumaiPennScholarship(Boolean.TRUE.equals(isGovtSchool) && Gender.FEMALE.equals(gender));
        }

//        // Constraint for labourwelfare
//        if (Boolean.TRUE.equals(purposeCheck.getLabourWelfareScholarship())) {
//            // Constraint
//        }
//
//        // Constraint for tailorwelfare
//        if (Boolean.TRUE.equals(purposeCheck.getTailorWelfareScholarship())) {
//            // Constraint
//        }
        // Constraint for farmerwelfare
//        if (Boolean.TRUE.equals(purposeCheck.getFarmerWelfareScholarship())) {
//            // Constraint
//        }

        return purposeCheck;
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
