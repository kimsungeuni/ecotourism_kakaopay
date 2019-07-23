package com.kakaopay.ecotourism.region.web;

import com.kakaopay.ecotourism.region.dto.RegionDto;
import com.kakaopay.ecotourism.region.entity.Region;
import com.kakaopay.ecotourism.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;
    private final ModelMapper modelMapper;

    @PostMapping
    public RegionDto.SimpleRes createRegion(@RequestBody RegionDto.Save saveDto) {
        return modelMapper.map(regionService.createRegion(saveDto), RegionDto.SimpleRes.class);
    }

    @GetMapping("/{regionId}")
    public RegionDto.Res getRegion(@PathVariable Long regionId) {
        return modelMapper.map(regionService.getRegion(regionId), RegionDto.Res.class);
    }

    @GetMapping
    public List<RegionDto.Res> getRegions(@RequestBody RegionDto.Search searchDto) {

        List<Region> regions = regionService.searchRegions(searchDto);

        List<RegionDto.Res> collect =
                regions.stream().map(r -> modelMapper.map(r, RegionDto.Res.class)).collect(Collectors.toList());

        return collect;
    }

    @PutMapping("/{regionId}")
    public RegionDto.Res updateRegion(@PathVariable Long regionId, @RequestBody RegionDto.Save saveDto) {
        return modelMapper.map(regionService.updateRegion(regionId, saveDto), RegionDto.Res.class);
    }

    @DeleteMapping("/{regionId}")
    public RegionDto.Res deleteRegion(@PathVariable Long regionId) {
        return modelMapper.map(regionService.deleteRegion(regionId), RegionDto.Res.class);
    }


}
