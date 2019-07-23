package com.kakaopay.ecotourism.commons.components;

import com.kakaopay.ecotourism.program.dto.ProgramDto;
import com.kakaopay.ecotourism.program.entity.Program;
import com.opencsv.CSVReader;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.util.Lists;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CsvConverter {
    private List<String[]> datas = null;

    public CsvConverter(){
        String path = new File("src/main/resources/static/서버개발_사전과제2_2017년_국립공원_생태관광_정보.csv").getAbsolutePath();
        try(CSVReader reader =  new CSVReader(new InputStreamReader(new FileInputStream(path), "EUC-KR"), ',', '\"', 1)){
            datas = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getRegions(){
        Set<String> regions = new HashSet<>();
        for(String[] data : datas) {
            String str = data[3];
            if(str.indexOf(",") < 0) {
                if(!regions.contains(str.trim())) regions.add(str.trim());
            } else {
                String[] split = str.split(",");
                String key = split[0].split(" ")[0];
                for(int i=0; i<split.length;i++){
                    if(i == 0) {
                        if(!regions.contains(split[i].trim())) regions.add(split[i].trim());
                    }
                    else {
                        if(!regions.contains((key + split[i]).trim())) regions.add((key + split[i]).trim());
                    }
                }
            }
        }
        return regions;
    }

    public List<String> regionNameToList(String regionName){
        List<String> regions = new ArrayList<>();
        if(regionName.indexOf(",") < 0) {
            if(!regions.contains(regionName.trim())) regions.add(regionName.trim());
        } else {
            String[] split = regionName.split(",");
            String key = split[0].split(" ")[0];
            for(int i=0; i<split.length;i++){
                if(i == 0) {
                    if(!regions.contains(split[i].trim())) regions.add(split[i].trim());
                }
                else {
                    if(!regions.contains((key + split[i]).trim())) regions.add((key + split[i]).trim());
                }
            }
        }
        return regions;
    }
}
