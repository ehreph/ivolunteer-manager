package com.ivolunteer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class GoalRequirementsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalRequirements.class);
        GoalRequirements goalRequirements1 = new GoalRequirements();
        goalRequirements1.setId(1L);
        GoalRequirements goalRequirements2 = new GoalRequirements();
        goalRequirements2.setId(goalRequirements1.getId());
        assertThat(goalRequirements1).isEqualTo(goalRequirements2);
        goalRequirements2.setId(2L);
        assertThat(goalRequirements1).isNotEqualTo(goalRequirements2);
        goalRequirements1.setId(null);
        assertThat(goalRequirements1).isNotEqualTo(goalRequirements2);
    }
}
