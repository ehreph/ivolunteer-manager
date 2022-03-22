package com.ivolunteer.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class GoalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalDTO.class);
        GoalDTO goalDTO1 = new GoalDTO();
        goalDTO1.setId(1L);
        GoalDTO goalDTO2 = new GoalDTO();
        assertThat(goalDTO1).isNotEqualTo(goalDTO2);
        goalDTO2.setId(goalDTO1.getId());
        assertThat(goalDTO1).isEqualTo(goalDTO2);
        goalDTO2.setId(2L);
        assertThat(goalDTO1).isNotEqualTo(goalDTO2);
        goalDTO1.setId(null);
        assertThat(goalDTO1).isNotEqualTo(goalDTO2);
    }
}
