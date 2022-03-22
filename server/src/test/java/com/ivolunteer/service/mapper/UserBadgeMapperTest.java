package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserBadgeMapperTest {

    private UserBadgeMapper userBadgeMapper;

    @BeforeEach
    public void setUp() {
        userBadgeMapper = new UserBadgeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userBadgeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userBadgeMapper.fromId(null)).isNull();
    }
}
