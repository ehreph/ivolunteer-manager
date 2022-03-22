package com.ivolunteer.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class GoalAwardDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalAwardDTO.class);
        GoalAwardDTO goalAwardDTO1 = new GoalAwardDTO();
        goalAwardDTO1.setId(1L);
        GoalAwardDTO goalAwardDTO2 = new GoalAwardDTO();
        assertThat(goalAwardDTO1).isNotEqualTo(goalAwardDTO2);
        goalAwardDTO2.setId(goalAwardDTO1.getId());
        assertThat(goalAwardDTO1).isEqualTo(goalAwardDTO2);
        goalAwardDTO2.setId(2L);
        assertThat(goalAwardDTO1).isNotEqualTo(goalAwardDTO2);
        goalAwardDTO1.setId(null);
        assertThat(goalAwardDTO1).isNotEqualTo(goalAwardDTO2);
    }
}
