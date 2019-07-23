package com.kakaopay.ecotourism.region.service;

import com.kakaopay.ecotourism.region.dto.RegionDto;
import com.kakaopay.ecotourism.region.entity.Region;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegionService {
    Region createRegion(RegionDto.Save saveDto);
    Region getRegion(Long regionId);
    Region getRegionWithName(String name);
    List<Region> searchRegions(RegionDto.Search searchDto);
    Region updateRegion(Long regionId, RegionDto.Save saveDto);
    Region deleteRegion(Long regionId);
}
