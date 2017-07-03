package io.datapath.plugins.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by jackson on 03/07/17.
 */
@Component
public class ApplicationProperties {

    @Value("${spark.host}")
    private String sparkHost;

    @Value("${spark.port}")
    private Integer sparkPort;

    public String getSparkHost() {
        return sparkHost;
    }

    public void setSparkHost(String sparkHost) {
        this.sparkHost = sparkHost;
    }

    public Integer getSparkPort() {
        return sparkPort;
    }

    public void setSparkPort(Integer sparkPort) {
        this.sparkPort = sparkPort;
    }
}
