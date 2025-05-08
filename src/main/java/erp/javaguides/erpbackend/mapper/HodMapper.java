package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.requestDto.HodRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;
import erp.javaguides.erpbackend.entity.Hod;

public class HodMapper {
    
        public static HodResponseDto toHodResponseDto(Hod hod) {
            HodResponseDto hodResponseDto = new HodResponseDto();
            hodResponseDto.setHodId(hod.getHodId());
            hodResponseDto.setFirstName(hod.getFirstName());
            hodResponseDto.setLastName(hod.getLastName());
            hodResponseDto.setEmail(hod.getEmail());
            hodResponseDto.setMobileNumber(hod.getMobileNumber());
            hodResponseDto.setDiscipline(hod.getDiscipline());
            return hodResponseDto;
        }
    
        public static Hod toHod(HodRequestDto hodRequestDto) {
            Hod hod = new Hod();
            hod.setFirstName(hodRequestDto.getFirstName());
            hod.setLastName(hodRequestDto.getLastName());
            hod.setEmail(hodRequestDto.getEmail());
            hod.setMobileNumber(hodRequestDto.getMobileNumber());
            hod.setDiscipline(hodRequestDto.getDiscipline());
            return hod;
        }
}
