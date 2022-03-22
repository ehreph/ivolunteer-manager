package com.ivolunteer.service.dto;

import com.ivolunteer.user.dto.UserGoalsDTO;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class UserGoalsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGoalsDTO.class);
        UserGoalsDTO userGoalsDTO1 = new UserGoalsDTO();
        userGoalsDTO1.setId(1L);
        UserGoalsDTO userGoalsDTO2 = new UserGoalsDTO();
        assertThat(userGoalsDTO1).isNotEqualTo(userGoalsDTO2);
        userGoalsDTO2.setId(userGoalsDTO1.getId());
        assertThat(userGoalsDTO1).isEqualTo(userGoalsDTO2);
        userGoalsDTO2.setId(2L);
        assertThat(userGoalsDTO1).isNotEqualTo(userGoalsDTO2);
        userGoalsDTO1.setId(null);
        assertThat(userGoalsDTO1).isNotEqualTo(userGoalsDTO2);
    }
}
