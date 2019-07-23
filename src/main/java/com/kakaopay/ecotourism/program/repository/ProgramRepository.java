package com.kakaopay.ecotourism.program.repository;

import com.kakaopay.ecotourism.program.dto.ProgramDto;
import com.kakaopay.ecotourism.program.entity.Program;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query("SELECT r.name, count(r.name) from Program p, Region r where p.region = r.id and p.content like %:keyword%")
    List<String[]> findAllByKeywordCountContent(String keyword);

    @Query("SELECT count(distinct p.content) from Program p where p.content like %:keyword%")
    String findByKeywordCountDetail(String keyword);
}
