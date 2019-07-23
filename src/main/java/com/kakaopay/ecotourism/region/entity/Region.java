package com.kakaopay.ecotourism.region.entity;

import com.kakaopay.ecotourism.commons.entity.BaseEntity;
import com.kakaopay.ecotourism.program.entity.Program;
import lombok.*;
import org.assertj.core.util.Sets;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Where(clause = "enabled = true")
@NoArgsConstructor
@AllArgsConstructor
public class Region extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<Program> programs = Sets.newHashSet();

}
