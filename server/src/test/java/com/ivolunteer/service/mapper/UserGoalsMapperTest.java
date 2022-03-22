package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserGoalsMapperTest {

    private UserGoalsMapper userGoalsMapper;

    @BeforeEach
    public void setUp() {
        userGoalsMapper = new UserGoalsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userGoalsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userGoalsMapper.fromId(null)).isNull();
    }
}
