package com.karim.management.ms01.web.rest;

import static com.karim.management.ms01.domain.WebsiteAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karim.management.ms01.IntegrationTest;
import com.karim.management.ms01.domain.Website;
import com.karim.management.ms01.repository.WebsiteRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WebsiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WebsiteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/websites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWebsiteMockMvc;

    private Website website;

    private Website insertedWebsite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Website createEntity() {
        return new Website().name(DEFAULT_NAME).url(DEFAULT_URL).ownerEmail(DEFAULT_OWNER_EMAIL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Website createUpdatedEntity() {
        return new Website().name(UPDATED_NAME).url(UPDATED_URL).ownerEmail(UPDATED_OWNER_EMAIL);
    }

    @BeforeEach
    public void initTest() {
        website = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedWebsite != null) {
            websiteRepository.delete(insertedWebsite);
            insertedWebsite = null;
        }
    }

    @Test
    @Transactional
    void getAllWebsites() throws Exception {
        // Initialize the database
        insertedWebsite = websiteRepository.saveAndFlush(website);

        // Get all the websiteList
        restWebsiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(website.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].ownerEmail").value(hasItem(DEFAULT_OWNER_EMAIL)));
    }

    @Test
    @Transactional
    void getWebsite() throws Exception {
        // Initialize the database
        insertedWebsite = websiteRepository.saveAndFlush(website);

        // Get the website
        restWebsiteMockMvc
            .perform(get(ENTITY_API_URL_ID, website.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(website.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.ownerEmail").value(DEFAULT_OWNER_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingWebsite() throws Exception {
        // Get the website
        restWebsiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    protected long getRepositoryCount() {
        return websiteRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Website getPersistedWebsite(Website website) {
        return websiteRepository.findById(website.getId()).orElseThrow();
    }

    protected void assertPersistedWebsiteToMatchAllProperties(Website expectedWebsite) {
        assertWebsiteAllPropertiesEquals(expectedWebsite, getPersistedWebsite(expectedWebsite));
    }

    protected void assertPersistedWebsiteToMatchUpdatableProperties(Website expectedWebsite) {
        assertWebsiteAllUpdatablePropertiesEquals(expectedWebsite, getPersistedWebsite(expectedWebsite));
    }
}
