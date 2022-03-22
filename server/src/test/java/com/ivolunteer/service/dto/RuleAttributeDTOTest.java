package com.ivolunteer.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class RuleAttributeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleAttributeDTO.class);
        RuleAttributeDTO ruleAttributeDTO1 = new RuleAttributeDTO();
        ruleAttributeDTO1.setId(1L);
        RuleAttributeDTO ruleAttributeDTO2 = new RuleAttributeDTO();
        assertThat(ruleAttributeDTO1).isNotEqualTo(ruleAttributeDTO2);
        ruleAttributeDTO2.setId(ruleAttributeDTO1.getId());
        assertThat(ruleAttributeDTO1).isEqualTo(ruleAttributeDTO2);
        ruleAttributeDTO2.setId(2L);
        assertThat(ruleAttributeDTO1).isNotEqualTo(ruleAttributeDTO2);
        ruleAttributeDTO1.setId(null);
        assertThat(ruleAttributeDTO1).isNotEqualTo(ruleAttributeDTO2);
    }
}
