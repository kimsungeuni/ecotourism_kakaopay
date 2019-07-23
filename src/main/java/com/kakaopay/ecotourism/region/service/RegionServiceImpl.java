package com.kakaopay.ecotourism.region.service;

import com.kakaopay.ecotourism.region.dto.RegionDto;
import com.kakaopay.ecotourism.region.entity.Region;
import com.kakaopay.ecotourism.region.repository.RegionRepository;
import com.kakaopay.ecotourism.commons.jpa.Restrictions;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Region createRegion(RegionDto.Save saveDto) {
        Region region = modelMapper.map(saveDto, Region.class);
        return regionRepository.save(region);
    }

    public Region getRegion(Long regionId){
        return regionRepository.findById(regionId).orElseThrow(IllegalArgumentException::new);
    }

    public Region getRegionWithName(String name){
        return regionRepository.findByName(name);
    }

    public List<Region> searchRegions(RegionDto.Search searchDto) {
        Restrictions restrictions = new Restrictions();

        if(!searchDto.getRegion().isEmpty())
            restrictions.like("name", "%"+searchDto.getRegion()+"%");

        return regionRepository.findAll(restrictions.output());
    }

    @Transactional
    public Region updateRegion(Long regionId, RegionDto.Save saveDto) {
        Region region = this.getRegion(regionId);
        modelMapper.map(saveDto, region);
        return regionRepository.save(region);
    }

    @Transactional
    public Region deleteRegion(Long regionId) {
        Region region = this.getRegion(regionId);
        region.setEnabled(false);
        region.setPrograms(null);
        return regionRepository.save(region);
    }

}
