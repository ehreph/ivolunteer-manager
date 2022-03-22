package com.ivolunteer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class RuleAttributeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleAttribute.class);
        RuleAttribute ruleAttribute1 = new RuleAttribute();
        ruleAttribute1.setId(1L);
        RuleAttribute ruleAttribute2 = new RuleAttribute();
        ruleAttribute2.setId(ruleAttribute1.getId());
        assertThat(ruleAttribute1).isEqualTo(ruleAttribute2);
        ruleAttribute2.setId(2L);
        assertThat(ruleAttribute1).isNotEqualTo(ruleAttribute2);
        ruleAttribute1.setId(null);
        assertThat(ruleAttribute1).isNotEqualTo(ruleAttribute2);
    }
}
