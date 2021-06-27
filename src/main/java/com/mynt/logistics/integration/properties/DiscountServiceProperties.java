package com.mynt.logistics.integration.properties;

import com.mynt.logistics.integration.properties.DownstreamProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "discount-service")
public class DiscountServiceProperties extends DownstreamProperties {
    @Value("${host:mynt-exam.mocklab.io}")
    private String host;
    @Value("${protocol:https}")
    private String protocol;
    @Value("${port:443}")
    private String port;
    @Value("${protocol:apikey}")
    private String apikey;

}
