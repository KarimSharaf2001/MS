package com.karim.management.ms01.web.rest;

import com.karim.management.ms01.domain.Website;
import com.karim.management.ms01.repository.WebsiteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.karim.management.ms01.domain.Website}.
 */
@RestController
@RequestMapping("/api/websites")
@Transactional
public class WebsiteResource {

    private static final Logger LOG = LoggerFactory.getLogger(WebsiteResource.class);

    private final WebsiteRepository websiteRepository;

    public WebsiteResource(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    /**
     * {@code GET  /websites} : get all the websites.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of websites in body.
     */
    @GetMapping("")
    public List<Website> getAllWebsites() {
        LOG.debug("REST request to get all Websites");
        return websiteRepository.findAll();
    }

    /**
     * {@code GET  /websites/:id} : get the "id" website.
     *
     * @param id the id of the website to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the website, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Website> getWebsite(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Website : {}", id);
        Optional<Website> website = websiteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(website);
    }
}
