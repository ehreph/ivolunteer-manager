package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompetenceMapperTest {

    private CompetenceMapper competenceMapper;

    @BeforeEach
    public void setUp() {
        competenceMapper = new CompetenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(competenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(competenceMapper.fromId(null)).isNull();
    }
}
