package com.ivolunteer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class GoalAwardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalAward.class);
        GoalAward goalAward1 = new GoalAward();
        goalAward1.setId(1L);
        GoalAward goalAward2 = new GoalAward();
        goalAward2.setId(goalAward1.getId());
        assertThat(goalAward1).isEqualTo(goalAward2);
        goalAward2.setId(2L);
        assertThat(goalAward1).isNotEqualTo(goalAward2);
        goalAward1.setId(null);
        assertThat(goalAward1).isNotEqualTo(goalAward2);
    }
}
