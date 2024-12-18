package com.karim.management.ms01.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WebsiteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Website getWebsiteSample1() {
        return new Website().id(1L).name("name1").url("url1").ownerEmail("ownerEmail1");
    }

    public static Website getWebsiteSample2() {
        return new Website().id(2L).name("name2").url("url2").ownerEmail("ownerEmail2");
    }

    public static Website getWebsiteRandomSampleGenerator() {
        return new Website()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .url(UUID.randomUUID().toString())
            .ownerEmail(UUID.randomUUID().toString());
    }
}
