package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;

import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    List<AuthenticationDto> addStudentAuthenticationFromExcel(InputStream is);
}
