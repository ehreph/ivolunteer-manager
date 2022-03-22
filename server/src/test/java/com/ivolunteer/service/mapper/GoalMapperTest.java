package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GoalMapperTest {

    private GoalMapper goalMapper;

    @BeforeEach
    public void setUp() {
        goalMapper = new GoalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(goalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(goalMapper.fromId(null)).isNull();
    }
}
