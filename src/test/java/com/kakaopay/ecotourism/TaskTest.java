package com.kakaopay.ecotourism;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kakaopay.ecotourism.program.dto.ProgramDto;
import com.kakaopay.ecotourism.region.dto.RegionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TaskTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    // 1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API를 개발하세요.
    @Test
    public void test1() throws Exception {
        mockMvc.perform(post("/api/ecotours/csv")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(null)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 2. 생태 관광정보 데이터를 조회/추가/수정할 수 있는 API를 개발하세요. o 단, 조회는 서비스 지역 코드를 기준으로 검색합니다
    @Test
    public void test2() throws Exception {
        //create region
        RegionDto.Save region = new RegionDto.Save();
        region.setName("서울특별시 강동구");
        mockMvc.perform(post("/api/regions")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(region)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));

        //get region
        mockMvc.perform(get("/api/regions/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(region)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));

        //update region
        region.setName("서울특별시 송파구");
        mockMvc.perform(put("/api/regions/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(region)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));

        //create region
        ProgramDto.Save program = new ProgramDto.Save();
        program.setName("한강 체험");
        program.setTheme(Lists.newArrayList("한강테마"));
        program.setContent("한강에서 놀기");
        program.setContent("한강에서 자세히 놀기");
        mockMvc.perform(post("/api/regions/1/programs")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(program)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));

        //get region
        mockMvc.perform(get("/api/regions/programs/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));

        //update region
        program.setName("한강 가기");
        mockMvc.perform(put("/api/regions/programs/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(program)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));


    }
    // 3.생태 관광지 중에 서비스 지역 컬럼에서 특정 지역에서 진행되는 프로그램명과 테마를 출력하는 API를 개발하세요.  o 예를들어, “평창군”이라는 문자열을 입력 받으면 아래와 같은 결과를 출력합니다. o 단, 출력 결과에 지역은 지역 코드를 출력합니다.
    @Test
    public void test3() throws Exception {
        RegionDto.Search search = new RegionDto.Search();
        search.setRegion("경기도");

        mockMvc.perform(get("/api/regions")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(search)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));
    }
    //4. 생태 정보 데이터에 "프로그램 소개” 컬럼에서 특정 문자열이 포함된 레코드에서 서비스 지역 개수를 세어 출력하는 API를 개발하세요. o 예를 들어, “세계문화유산” 문자열을 입력 받아 포함된 레코드에서 서비스 지역 개수와 지역정보를 출력
    @Test
    public void test4() throws Exception {
        ProgramDto.Search search = new ProgramDto.Search();
        search.setKeyword("세계문화유산");

        mockMvc.perform(get("/api/regions/programs/search/content")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(search)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));
    }

    //5. 모든 레코드에 프로그램 상세 정보를 읽어와서 입력 단어의 출현빈도수를 계산하여 출력 하는 API를 개발하세요
    @Test
    public void test5() throws Exception {
        ProgramDto.Search search = new ProgramDto.Search();
        search.setKeyword("문화");

        mockMvc.perform(get("/api/regions/programs/search/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(objectMapper.writeValueAsString(search)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));
    }

}

