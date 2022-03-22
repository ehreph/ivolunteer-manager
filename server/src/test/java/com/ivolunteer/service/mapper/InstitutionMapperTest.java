package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InstitutionMapperTest {

    private InstitutionMapper institutionMapper;

    @BeforeEach
    public void setUp() {
        institutionMapper = new InstitutionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(institutionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(institutionMapper.fromId(null)).isNull();
    }
}
