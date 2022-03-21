package com.sgeapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sgeapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocialOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialOrganizationDTO.class);
        SocialOrganizationDTO socialOrganizationDTO1 = new SocialOrganizationDTO();
        socialOrganizationDTO1.setId(1L);
        SocialOrganizationDTO socialOrganizationDTO2 = new SocialOrganizationDTO();
        assertThat(socialOrganizationDTO1).isNotEqualTo(socialOrganizationDTO2);
        socialOrganizationDTO2.setId(socialOrganizationDTO1.getId());
        assertThat(socialOrganizationDTO1).isEqualTo(socialOrganizationDTO2);
        socialOrganizationDTO2.setId(2L);
        assertThat(socialOrganizationDTO1).isNotEqualTo(socialOrganizationDTO2);
        socialOrganizationDTO1.setId(null);
        assertThat(socialOrganizationDTO1).isNotEqualTo(socialOrganizationDTO2);
    }
}
