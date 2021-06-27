package com.mynt.logistics.integration;

import com.mynt.logistics.integration.properties.DownstreamProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.time.Duration;


@Component
public class DownstreamConfig {


    private DownstreamProperties downstreamProperties;


    @Autowired
    public DownstreamConfig(DownstreamProperties downstreamProperties) {
        this.downstreamProperties = downstreamProperties;
    }

    @Bean
    public HttpClient getHttpClient() {
        return  HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(downstreamProperties.getTimeout()))
                .build();
    }
}
