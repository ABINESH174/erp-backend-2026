package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.AcademicsDto;

import erp.javaguides.erpbackend.entity.Academics;

import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.AcademicsMapper;
import erp.javaguides.erpbackend.repository.AcademicsRepository;
import erp.javaguides.erpbackend.service.AcademicsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AcademicsServiceImpl implements AcademicsService {
    private AcademicsRepository academicsRepository;
    @Override
    public AcademicsDto createAcademics(AcademicsDto academicsDto) throws Exception{
        Optional<Academics> optionalAcademics = academicsRepository.findById(academicsDto.getRegisterNo());
        if(optionalAcademics.isPresent()){
            throw new Exception("Register no already exists");
        }
        Academics academics = AcademicsMapper.mapToAcademics(academicsDto);
        Academics savedAcademics=academicsRepository.save(academics);
        return AcademicsMapper.mapToAcademicsDto(savedAcademics);
    }

    @Override
    public AcademicsDto getAcademicsById(String registerNo) {
        Academics academics = academicsRepository.findById(registerNo)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + registerNo));
        return AcademicsMapper.mapToAcademicsDto(academics);
    }

    @Override
    public List<AcademicsDto> getAllAcademics() {
        List<Academics> academic=academicsRepository.findAll();
        return  academic.stream().map((academics) -> AcademicsMapper.mapToAcademicsDto(academics))
                .collect(Collectors.toList());
    }

    @Override
    public AcademicsDto updateAcademics(String registerNo, AcademicsDto updatedAcademics) {
        Academics academics= academicsRepository.findById(registerNo)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + registerNo));

        Academics updatedAcademicsObj=academicsRepository.save(academics);
        return AcademicsMapper.mapToAcademicsDto(updatedAcademicsObj);
    }

    @Override
    public void deleteAcademics(String registerNo) {
        Academics academics = academicsRepository.findById(registerNo)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + registerNo));
        academicsRepository.deleteById(registerNo);
    }
}
