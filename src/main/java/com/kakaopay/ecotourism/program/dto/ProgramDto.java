package com.kakaopay.ecotourism.program.dto;

import com.kakaopay.ecotourism.region.dto.RegionDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProgramDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Save {
        private String name;
        private List<String> theme;
        private String content;
        private String contentDetail;
    }

    @Getter
    @Setter
    public static class Res {
        private Long id;
        private String name;
        private List<String> theme;
        private String content;
        private String contentDetail;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private RegionDto.SimpleRes region;
    }

    @Getter
    @Setter
    public static class Search {
        private String keyword;
        private List<ProgramDto.SearchRes> programs;
    }

    @Getter
    @Setter
    public static class SearchRes {
        private String region;
        private String count;
    }
}
