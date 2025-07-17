package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
import erp.javaguides.erpbackend.service.ExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public List<AuthenticationDto> addStudentAuthenticationFromExcel(InputStream is) {
            List<AuthenticationDto> authenticationDtos = new ArrayList<>();
            try {
                Workbook workbook = new XSSFWorkbook(is); // Read Excel workbook
                Sheet sheet = workbook.getSheetAt(0);     // Read first sheet

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // Skip header

                    AuthenticationDto authenticationDto = new AuthenticationDto(
                            String.valueOf((long) row.getCell(0).getNumericCellValue()),
                            String.valueOf((long) row.getCell(1).getNumericCellValue()),
                            "STUDENT"
                    );

                    authenticationDtos.add(authenticationDto);
                }
                workbook.close();
            } catch (Exception e) {
                throw new RuntimeException("Error reading excel file :"+e.getMessage());
            }
            return authenticationDtos;
    }
}
