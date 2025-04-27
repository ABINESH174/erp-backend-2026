package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.requestDto.BonafideDto;
import erp.javaguides.erpbackend.entity.Bonafide;

public class BonafideMapper {
    public static BonafideDto mapToBonafideDto(Bonafide bonafide){
        return new BonafideDto(
                bonafide.getBonafideId(),
                bonafide.getStudent().getRegisterNo(),
                bonafide.getPurpose(),
                bonafide.getBonafideStatus(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static Bonafide mapToBonafide(BonafideDto bonafideDto){
        return new Bonafide(
                bonafideDto.getBonafideId(),
                bonafideDto.getRegisterNo(),
                bonafideDto.getPurpose(),
                bonafideDto.getBonafideStatus(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
