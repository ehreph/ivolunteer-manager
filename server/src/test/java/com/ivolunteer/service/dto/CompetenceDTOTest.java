package com.ivolunteer.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class CompetenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetenceDTO.class);
        CompetenceDTO competenceDTO1 = new CompetenceDTO();
        competenceDTO1.setId(1L);
        CompetenceDTO competenceDTO2 = new CompetenceDTO();
        assertThat(competenceDTO1).isNotEqualTo(competenceDTO2);
        competenceDTO2.setId(competenceDTO1.getId());
        assertThat(competenceDTO1).isEqualTo(competenceDTO2);
        competenceDTO2.setId(2L);
        assertThat(competenceDTO1).isNotEqualTo(competenceDTO2);
        competenceDTO1.setId(null);
        assertThat(competenceDTO1).isNotEqualTo(competenceDTO2);
    }
}
