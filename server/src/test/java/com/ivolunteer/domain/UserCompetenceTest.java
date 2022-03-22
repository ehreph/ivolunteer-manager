package com.ivolunteer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class UserCompetenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCompetence.class);
        UserCompetence userCompetence1 = new UserCompetence();
        userCompetence1.setId(1L);
        UserCompetence userCompetence2 = new UserCompetence();
        userCompetence2.setId(userCompetence1.getId());
        assertThat(userCompetence1).isEqualTo(userCompetence2);
        userCompetence2.setId(2L);
        assertThat(userCompetence1).isNotEqualTo(userCompetence2);
        userCompetence1.setId(null);
        assertThat(userCompetence1).isNotEqualTo(userCompetence2);
    }
}
