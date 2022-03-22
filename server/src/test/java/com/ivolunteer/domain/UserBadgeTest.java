package com.ivolunteer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ivolunteer.web.rest.TestUtil;

public class UserBadgeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBadge.class);
        UserBadge userBadge1 = new UserBadge();
        userBadge1.setId(1L);
        UserBadge userBadge2 = new UserBadge();
        userBadge2.setId(userBadge1.getId());
        assertThat(userBadge1).isEqualTo(userBadge2);
        userBadge2.setId(2L);
        assertThat(userBadge1).isNotEqualTo(userBadge2);
        userBadge1.setId(null);
        assertThat(userBadge1).isNotEqualTo(userBadge2);
    }
}
