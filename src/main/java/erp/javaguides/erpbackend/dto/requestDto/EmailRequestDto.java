package erp.javaguides.erpbackend.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {
    private Long bonafideId;
    private String registerNo;
    private String status;
}
