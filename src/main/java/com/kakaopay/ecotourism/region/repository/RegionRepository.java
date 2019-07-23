package com.kakaopay.ecotourism.region.repository;

import com.kakaopay.ecotourism.region.entity.Region;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends DataTablesRepository<Region, Long>, JpaRepository<Region, Long> {
    Region findByName(String name);
}
