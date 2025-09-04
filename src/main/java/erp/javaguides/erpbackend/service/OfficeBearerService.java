package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.entity.OfficeBearer;

import java.util.List;

public interface OfficeBearerService {

    OfficeBearer saveOfficeBearer(OfficeBearer officeBearer) throws Exception;

    OfficeBearer getOfficeBearerByEmail(String email);

    List<OfficeBearer> getAllOfficeBearers();
}
