package com.karim.management.ms01.domain;

import static com.karim.management.ms01.domain.WebsiteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.karim.management.ms01.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WebsiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Website.class);
        Website website1 = getWebsiteSample1();
        Website website2 = new Website();
        assertThat(website1).isNotEqualTo(website2);

        website2.setId(website1.getId());
        assertThat(website1).isEqualTo(website2);

        website2 = getWebsiteSample2();
        assertThat(website1).isNotEqualTo(website2);
    }
}
