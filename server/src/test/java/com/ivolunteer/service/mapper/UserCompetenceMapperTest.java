package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCompetenceMapperTest {

    private UserCompetenceMapper userCompetenceMapper;

    @BeforeEach
    public void setUp() {
        userCompetenceMapper = new UserCompetenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userCompetenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userCompetenceMapper.fromId(null)).isNull();
    }
}
