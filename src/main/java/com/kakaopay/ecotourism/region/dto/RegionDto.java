package com.kakaopay.ecotourism.region.dto;

import com.kakaopay.ecotourism.program.dto.ProgramDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

public class RegionDto {
    @Getter
    @Setter
    public static class Save {
        private String name;
    }

    @Getter
    @Setter
    public static class Res {
        private Long id;
        private String name;
        private Set<ProgramDto.Res> programs;
        private LocalDateTime createdAt;
        protected LocalDateTime updatedAt;
    }
    @Getter
    @Setter
    public static class SimpleRes {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    public static class Search {
        private String region;
    }

}
