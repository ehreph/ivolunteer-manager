package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BadgeMapperTest {

    private BadgeMapper badgeMapper;

    @BeforeEach
    public void setUp() {
        badgeMapper = new BadgeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(badgeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(badgeMapper.fromId(null)).isNull();
    }
}
