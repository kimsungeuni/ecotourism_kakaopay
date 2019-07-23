package com.kakaopay.ecotourism.program.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kakaopay.ecotourism.program.dto.ProgramDto;
import com.kakaopay.ecotourism.program.service.ProgramService;
import com.kakaopay.ecotourism.program.service.ProgramServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;
    private final ModelMapper modelMapper;

    @PostMapping("/{regionId}/programs")
    public ProgramDto.Res createProgram(@PathVariable Long regionId, @RequestBody ProgramDto.Save saveDto) {
        return modelMapper.map(programService.createProgram(regionId, saveDto), ProgramDto.Res.class);
    }

    @GetMapping("/programs/{programId}")
    public ProgramDto.Res createProgram(@PathVariable Long programId) {
        return modelMapper.map(programService.getProgram(programId), ProgramDto.Res.class);
    }

    @GetMapping("/programs/search/content")
    public ProgramDto.Search searchForContent(@RequestBody ProgramDto.Search searchDto) {
        List<String[]> datas =programService.searchForContent(searchDto);

        List<ProgramDto.SearchRes> programs = Lists.newArrayList();
        for(String[] data : datas){
            ProgramDto.SearchRes searchRes = new ProgramDto.SearchRes();
            searchRes.setRegion(data[0]);
            searchRes.setCount(data[1]);
            programs.add(searchRes);
        }
        searchDto.setPrograms(programs);

        return searchDto;
    }
    @GetMapping("/programs/search/detail")
    public HashMap<String, Object> searchForDetail(@RequestBody ProgramDto.Search searchDto) {
        String data = programService.searchForDetail(searchDto);
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", searchDto.getKeyword());
        map.put("count", data);

        return map;
    }


    @PutMapping("/programs/{programId}")
    public ProgramDto.Res updateProgram(@PathVariable Long programId, @RequestBody ProgramDto.Save saveDto) {
        return modelMapper.map(programService.updateProgram(programId, saveDto), ProgramDto.Res.class);
    }

    @DeleteMapping("/programs/{programId}")
    public ProgramDto.Res deleteProgram(@PathVariable Long programId) {
        return modelMapper.map(programService.deleteProgram(programId), ProgramDto.Res.class);
    }

}
