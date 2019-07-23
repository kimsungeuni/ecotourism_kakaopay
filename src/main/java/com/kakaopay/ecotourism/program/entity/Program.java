package com.kakaopay.ecotourism.program.entity;

import com.google.common.collect.Lists;
import com.kakaopay.ecotourism.commons.entity.BaseEntity;
import com.kakaopay.ecotourism.region.entity.Region;
import lombok.*;
import org.assertj.core.util.Sets;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause = "enabled = true")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Program extends BaseEntity {
    private String name;

    private List<String> theme = Lists.newArrayList();
    @Lob
    private String content;
    @Lob
    private String contentDetail;
    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;
}
