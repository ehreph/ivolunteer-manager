package com.ivolunteer.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class UserCompetenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCompetenceDTO.class);
        UserCompetenceDTO userCompetenceDTO1 = new UserCompetenceDTO();
        userCompetenceDTO1.setId(1L);
        UserCompetenceDTO userCompetenceDTO2 = new UserCompetenceDTO();
        assertThat(userCompetenceDTO1).isNotEqualTo(userCompetenceDTO2);
        userCompetenceDTO2.setId(userCompetenceDTO1.getId());
        assertThat(userCompetenceDTO1).isEqualTo(userCompetenceDTO2);
        userCompetenceDTO2.setId(2L);
        assertThat(userCompetenceDTO1).isNotEqualTo(userCompetenceDTO2);
        userCompetenceDTO1.setId(null);
        assertThat(userCompetenceDTO1).isNotEqualTo(userCompetenceDTO2);
    }
}
