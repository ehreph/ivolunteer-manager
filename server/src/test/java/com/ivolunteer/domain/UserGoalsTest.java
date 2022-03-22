package com.ivolunteer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class UserGoalsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGoals.class);
        UserGoals userGoals1 = new UserGoals();
        userGoals1.setId(1L);
        UserGoals userGoals2 = new UserGoals();
        userGoals2.setId(userGoals1.getId());
        assertThat(userGoals1).isEqualTo(userGoals2);
        userGoals2.setId(2L);
        assertThat(userGoals1).isNotEqualTo(userGoals2);
        userGoals1.setId(null);
        assertThat(userGoals1).isNotEqualTo(userGoals2);
    }
}
