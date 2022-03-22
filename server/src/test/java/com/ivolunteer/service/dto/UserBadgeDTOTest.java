package com.ivolunteer.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class UserBadgeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBadgeDTO.class);
        UserBadgeDTO userBadgeDTO1 = new UserBadgeDTO();
        userBadgeDTO1.setId(1L);
        UserBadgeDTO userBadgeDTO2 = new UserBadgeDTO();
        assertThat(userBadgeDTO1).isNotEqualTo(userBadgeDTO2);
        userBadgeDTO2.setId(userBadgeDTO1.getId());
        assertThat(userBadgeDTO1).isEqualTo(userBadgeDTO2);
        userBadgeDTO2.setId(2L);
        assertThat(userBadgeDTO1).isNotEqualTo(userBadgeDTO2);
        userBadgeDTO1.setId(null);
        assertThat(userBadgeDTO1).isNotEqualTo(userBadgeDTO2);
    }
}
