package erp.javaguides.erpbackend.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import erp.javaguides.erpbackend.dto.requestDto.HodRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;
import erp.javaguides.erpbackend.entity.Hod;

public class HodMapper {
    
        public static HodResponseDto toHodResponseDto(Hod hod) {
            HodResponseDto hodResponseDto = new HodResponseDto();

            List<FacultyResponseDto> facultyResponseDtos = Optional.ofNullable(hod.getFaculties())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(FacultyMapper::mapToFacultyResponseDto)
                    .toList();
            hodResponseDto.setFaculties(facultyResponseDtos);
            
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
