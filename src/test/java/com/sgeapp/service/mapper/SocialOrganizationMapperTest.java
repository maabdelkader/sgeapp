package com.sgeapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocialOrganizationMapperTest {

    private SocialOrganizationMapper socialOrganizationMapper;

    @BeforeEach
    public void setUp() {
        socialOrganizationMapper = new SocialOrganizationMapperImpl();
    }
}
