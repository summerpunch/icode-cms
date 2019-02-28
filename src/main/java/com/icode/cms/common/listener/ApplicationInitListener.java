package com.icode.cms.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ApplicationInitListener
 *
 * @author pengren
 */
public class ApplicationInitListener implements ApplicationListener<ApplicationReadyEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationInitListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        LOGGER.info("Application Init Ready");

        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();

        LOGGER.info("profiles..................{}", profiles);

        DictionaryClientHandle dictionaryClientHandle = applicationContext.getBean(DictionaryClientHandle.class);

        if (profiles != null && profiles.length > 0) {
            dictionaryClientHandle.loadLocalData(applicationContext, profiles[0]);
        } else {
            LOGGER.error("Spring Boot Application Load Profiles Error!");
        }
    }
}
