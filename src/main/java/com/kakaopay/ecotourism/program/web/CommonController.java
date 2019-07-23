package com.kakaopay.ecotourism.program.web;

import com.google.common.collect.Lists;
import com.kakaopay.ecotourism.commons.components.CsvConverter;
import com.kakaopay.ecotourism.program.dto.ProgramDto;
import com.kakaopay.ecotourism.program.service.ProgramService;
import com.kakaopay.ecotourism.region.dto.RegionDto;
import com.kakaopay.ecotourism.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/ecotours")
@RequiredArgsConstructor
public class CommonController {

    private final ProgramService programService;
    private final RegionService regionService;

    //1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API를 개발하세요.
    @PostMapping("/csv")
    public void createCsvToDb(){
        CsvConverter csvConverter = new CsvConverter();

        Set<String> regions = csvConverter.getRegions();
        //region create
        for(String region : regions){
            RegionDto.Save save = new RegionDto.Save();
            save.setName(region);
            regionService.createRegion(save);
        }
        //region create
        List<String[]> programs = csvConverter.getDatas();
        //theme create

        for(String[] data : programs){
            //theme create
            List<String> themes = Lists.newArrayList();
            for(String theme : data[2].split(",")){
                if(!theme.trim().isEmpty()) themes.add(theme);
            }
            ProgramDto.Save programSaveDto = ProgramDto.Save.builder()
                    .name(data[1])
                    .theme(themes)
                    .content(data[4])
                    .contentDetail(data[5])
                    .build();
            List<String> regionNames = csvConverter.regionNameToList(data[3]);
            for(String regionName : regionNames){
                Long regionId = regionService.getRegionWithName(regionName).getId();
                programService.createProgram(regionId, programSaveDto);
            }
        }
    }
}
