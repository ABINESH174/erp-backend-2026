package erp.javaguides.erpbackend.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
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

import com.itextpdf.layout.Document;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public BonafideResponseDto updateObRejectedBonafide(Long bonafideId, String registerNo,BonafideStatus status, String rejectionMessage) {
        try {
            Bonafide bonafide = bonafideRepository.findByBonafideIdAndStudentRegisterNo(bonafideId, registerNo)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Bonafide not found with ID: " + bonafideId + " and Register No: " + registerNo));
            bonafide.setBonafideStatus(status);
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
                    purposeCheck.setBcMbcDncPostMatricScholarship(false);
                    break;
                case "bonafide for sc/st post metric scholorship":
                    purposeCheck.setScStScaPostMatricScholarship(false);
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
        if (!(Boolean.FALSE.equals(purposeCheck.getBcMbcDncPostMatricScholarship()))) {
            String income = student.getIncome();
            BigDecimal incomeDecimal = new BigDecimal(income.trim());
            String community = student.getCommunity();

            boolean isCasteEligible = community != null && (
                    community.equalsIgnoreCase("bc") ||
                            community.equalsIgnoreCase("mbc") ||
                            community.equalsIgnoreCase("dnc")
            );

            boolean isIncomeEligible = incomeDecimal.compareTo(BigDecimal.valueOf(250000)) <= 0;
            purposeCheck.setBcMbcDncPostMatricScholarship(isCasteEligible && isIncomeEligible);
        }
        // Constraint for SC ST
        if (!(Boolean.FALSE.equals(purposeCheck.getScStScaPostMatricScholarship()))) {
            String income = student.getIncome();
            BigDecimal incomeDecimal = new BigDecimal(income.trim());
            String community = student.getCommunity();

            boolean isCasteEligible = community != null && (
                    community.equalsIgnoreCase("sc") ||
                            community.equalsIgnoreCase("st") ||
                            community.equalsIgnoreCase("sca")
            );

            boolean isIncomeEligible = incomeDecimal.compareTo(BigDecimal.valueOf(250000)) <= 0;

            purposeCheck.setScStScaPostMatricScholarship(isCasteEligible && isIncomeEligible);
        }

        // Constraint for TAMILPUDHALVAN
        if (!(Boolean.FALSE.equals(purposeCheck.getTamilPudhalvanScholarship()))) {
            Boolean isGovtSchool = student.getIsGovtSchool(); // Boolean field
            Gender gender = student.getGender();

            purposeCheck.setTamilPudhalvanScholarship(Boolean.TRUE.equals(isGovtSchool) && Gender.Male.equals(gender));
        }

        // Constraint for PUDHUMAIPENN
        if (!(Boolean.FALSE.equals(purposeCheck.getPudhumaiPennScholarship()))) {
            Boolean isGovtSchool = student.getIsGovtSchool();
            Gender gender = student.getGender();

            purposeCheck.setPudhumaiPennScholarship(Boolean.TRUE.equals(isGovtSchool) && Gender.Female.equals(gender));
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
    @Override
    public List<BonafideResponseDto> getPrincipalApprovedBonafides(){
        return bonafideRepository.findByBonafideStatus(BonafideStatus.PRINCIPAL_APPROVED)
                .stream()
                .map(BonafideMapper::mapToBonafideResponseDto)
                .toList();
    }

    private String getYearFromSemester(String registerNo){
        Student student = studentRepository.findByRegisterNo(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Register No: " + registerNo));
        String semesterRoman = student.getSemester();
        int semesterNumber = switch (semesterRoman){
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            default -> 0;
        };
        int yearNumber = (semesterNumber + 1) / 2;
        return switch (yearNumber){
            case 1 -> "First";
            case 2 -> "Second";
            case 3 -> "Third";
            case 4 -> "Fourth";
            default -> "Invalid Year";
        };
    }

    //generate bonafide pdf
    @Override
    public byte[] generateBonafideCertificate(Long bonafideId,String registerNo) throws Exception{
        Bonafide bonafide = bonafideRepository.findByBonafideIdAndStudentRegisterNo(bonafideId,registerNo).orElseThrow(() -> new ResourceNotFoundException(
                "Bonafide not found with ID: " + bonafideId + " and Register No: " + registerNo));

        Student student = bonafide.getStudent();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        // Fonts
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont normal = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        document.setMargins(50,50,50,50);

        ImageData leftLogo = ImageDataFactory.create("src/main/resources/Images/TN_GOVERN.png");
        ImageData rightLogo = ImageDataFactory.create("src/main/resources/Images/acgcetlogo.png");
        Image leftImage = new Image(leftLogo).scaleToFit(70,70);
        Image rightImage = new Image(rightLogo).scaleToFit(70,70);


        // Header
        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{1, 4, 1}))
                .useAllAvailableWidth();

        headerTable.addCell(new Cell().add(leftImage).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
        Paragraph collegeHeader = new Paragraph()
                .add("Department of Technical Education, Tamilnadu\n")
                .add("Alagappa Chettiar Government College of Engineering & Technology,(AUTONOMOUS) Karaikudi - 630 003.\n")
                .add("(T.P.No:04565-224535, Fax No:04565-224528)\n")
                .add("AICTE and Permanently Affiliated to Anna University, Chennai - 600 025")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(normal)
                .setFontSize(10);
        headerTable.addCell(new Cell().add(collegeHeader).setBorder(Border.NO_BORDER));
        headerTable.addCell(new Cell().add(rightImage).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
        document.add(headerTable);
        document.add(new LineSeparator(new SolidLine()));

        // Certificate number and date
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Paragraph certInfo = new Paragraph()
                .add(new Text("CERTIFICATE NO: 1804/S1/2024").setFont(normal))
                .add(new Tab())
                .addTabStops(new TabStop(450, TabAlignment.RIGHT))
                .add(new Text("DATED: "+currentDate).setFont(normal))
                .setMarginTop(10);
        document.add(certInfo);

        // Title
        Paragraph title = new Paragraph("BONAFIDE CERTIFICATE")
                .setFont(bold)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setUnderline()
                .setMarginTop(20);
        document.add(title);

        String companyName = bonafide.getCompanyName();
        String bankNameForEducationalLoan = bonafide.getBankNameForEducationalLoan();
        String purpose = bonafide.getPurpose();
        String additionalPurpose = purpose;


        if(purpose.trim().equalsIgnoreCase("Bonafide for Internship") && companyName != null && !companyName.isBlank()){
            additionalPurpose = purpose + " at " + companyName;
        }else if(purpose.trim().equalsIgnoreCase("Educational Support") && bankNameForEducationalLoan != null && !bankNameForEducationalLoan.isBlank()){
            additionalPurpose = purpose + " from " + bankNameForEducationalLoan;
        }

        System.out.println("Purpose: " + purpose);
        System.out.println("Company Name: " + companyName);
        System.out.println("Bank Name: " + bankNameForEducationalLoan);
        System.out.println("Final Purpose: " + additionalPurpose);

        // Body
        String body = String.format(
                "This is to certify that Selvan. %s %s (Reg. No: %s) is studying in %s Year B.E. %s (Semester:%s) in this institution. " +
                        "He is a bonafide student of our college during the academic year %s.\n\n" +
                        "This certificate is issued to enable him to apply for %s.",
                student.getFirstName().toUpperCase(),
                student.getLastName().toUpperCase(),
                student.getRegisterNo(),
                getYearFromSemester(student.getRegisterNo()),
                student.getDiscipline(),
                student.getSemester(),
                bonafide.getAcademicYear(),
                additionalPurpose
        );

        document.add(new Paragraph(body)
                .setFont(normal)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setFontSize(11)
                .setMarginTop(20));

        // Footer
        Table footerTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .useAllAvailableWidth()
                .setMarginTop(50);

        footerTable.addCell(new Cell().add(new Paragraph("College Seal"))
                .setFont(normal).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
        footerTable.addCell(new Cell().add(new Paragraph("VICE PRINCIPAL"))
                .setFont(normal).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
        document.add(footerTable);

        document.add(new Paragraph("\nTo\nThe above Student")
                .setFont(normal).setTextAlignment(TextAlignment.LEFT));

        document.close();

        Bonafide savedBonafide = bonafideRepository.save(bonafide);
        String userFolderPath = Paths.get(FOLDERPATH ,savedBonafide.getStudent().getRegisterNo() , savedBonafide.getBonafideId().toString()).toString();
        Files.createDirectories(Paths.get(userFolderPath));
        String fileName = "bonafide_" + savedBonafide.getStudent().getFirstName() +"_"+ savedBonafide.getStudent().getLastName() + ".pdf";
        String bonafidePdfPath = Paths.get(userFolderPath,fileName).toString();
        savedBonafide.setGeneratedBonafideFilePath(bonafidePdfPath);
        bonafideRepository.save(savedBonafide);
        Files.write(Paths.get(bonafidePdfPath),byteArrayOutputStream.toByteArray());
        return byteArrayOutputStream.toByteArray();
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
