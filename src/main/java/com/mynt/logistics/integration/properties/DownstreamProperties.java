package com.mynt.logistics.integration.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "downstream")
public class DownstreamProperties {

    @Value("${timeout:5}")
    private int timeout;

}
