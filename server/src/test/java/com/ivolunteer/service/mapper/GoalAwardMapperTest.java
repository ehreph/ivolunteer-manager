package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GoalAwardMapperTest {

    private GoalAwardMapper goalAwardMapper;

    @BeforeEach
    public void setUp() {
        goalAwardMapper = new GoalAwardMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(goalAwardMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(goalAwardMapper.fromId(null)).isNull();
    }
}
