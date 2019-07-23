package com.kakaopay.ecotourism.program.service;

import com.kakaopay.ecotourism.program.dto.ProgramDto;
import com.kakaopay.ecotourism.program.entity.Program;
import com.kakaopay.ecotourism.program.repository.ProgramRepository;
import com.kakaopay.ecotourism.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService{
    private final ProgramRepository programRepository;
    private final RegionRepository regionRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Program createProgram(Long regionId, ProgramDto.Save saveDto){
        Program program = modelMapper.map(saveDto, Program.class);
        //region.setProgramDetail(modelMapper.map(saveDto, ProgramDetail.class));
        program.setRegion(regionRepository.findById(regionId).orElseThrow(IllegalArgumentException::new));
        return programRepository.save(program);
    }


    public Program getProgram(Long programId){
        return programRepository.findById(programId).orElseThrow(IllegalArgumentException::new);
    }

    public List<String[]> searchForContent(ProgramDto.Search searchDto) {

        if(!searchDto.getKeyword().isEmpty())
            return programRepository.findAllByKeywordCountContent(searchDto.getKeyword());

        return null;
    }
    public String searchForDetail(ProgramDto.Search searchDto){
        return programRepository.findByKeywordCountDetail(searchDto.getKeyword());
    }

    @Transactional
    public Program updateProgram(Long programId, ProgramDto.Save saveDto) {
        Program program = this.getProgram(programId);
        modelMapper.map(saveDto, program);
        return programRepository.save(program);
    }

    @Transactional
    public Program deleteProgram(Long programId) {
        Program program = this.getProgram(programId);
        program.setEnabled(false);
        return programRepository.save(program);
    }
}
