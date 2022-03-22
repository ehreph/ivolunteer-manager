package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RuleAttributeMapperTest {

    private RuleAttributeMapper ruleAttributeMapper;

    @BeforeEach
    public void setUp() {
        ruleAttributeMapper = new RuleAttributeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ruleAttributeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ruleAttributeMapper.fromId(null)).isNull();
    }
}
