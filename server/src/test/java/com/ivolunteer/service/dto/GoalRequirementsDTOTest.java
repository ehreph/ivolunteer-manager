package com.ivolunteer.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class GoalRequirementsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalRequirementsDTO.class);
        GoalRequirementsDTO goalRequirementsDTO1 = new GoalRequirementsDTO();
        goalRequirementsDTO1.setId(1L);
        GoalRequirementsDTO goalRequirementsDTO2 = new GoalRequirementsDTO();
        assertThat(goalRequirementsDTO1).isNotEqualTo(goalRequirementsDTO2);
        goalRequirementsDTO2.setId(goalRequirementsDTO1.getId());
        assertThat(goalRequirementsDTO1).isEqualTo(goalRequirementsDTO2);
        goalRequirementsDTO2.setId(2L);
        assertThat(goalRequirementsDTO1).isNotEqualTo(goalRequirementsDTO2);
        goalRequirementsDTO1.setId(null);
        assertThat(goalRequirementsDTO1).isNotEqualTo(goalRequirementsDTO2);
    }
}
