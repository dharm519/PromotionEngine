package com;
import com.configuration.ConfigurationProperties;
import com.configuration.ExternalConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ExternalConfigurationTest {

    private static final ConfigurationProperties configurationProperties =
            ExternalConfiguration.getInstance().getConfigurationProperties();

    @Test
    public void testConfig() {
        assertFalse(configurationProperties.getProducts().isEmpty());
    }
}
