package com.kakaopay.ecotourism.program.service;

import com.kakaopay.ecotourism.program.dto.ProgramDto;
import com.kakaopay.ecotourism.program.entity.Program;

import java.util.List;

public interface ProgramService {
    Program createProgram(Long regionId, ProgramDto.Save saveDto);
    Program getProgram(Long programId);
    List<String[]> searchForContent(ProgramDto.Search searchDto);
    String searchForDetail(ProgramDto.Search searchDto);
    Program updateProgram(Long programId, ProgramDto.Save saveDto);
    Program deleteProgram(Long programId);
}
