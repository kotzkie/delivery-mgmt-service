package com.mynt.logistics.integration.gateway;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.logistics.integration.DiscountDTO;
import com.mynt.logistics.integration.DownstreamException;
import com.mynt.logistics.integration.IDiscount;
import com.mynt.logistics.integration.gateway.IDiscountGateway;
import com.mynt.logistics.integration.properties.DiscountServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Slf4j
@Component
public class DiscountGateway implements IDiscountGateway {

    private HttpClient httpClient;

    private DiscountServiceProperties discountServiceProperties;

    @Autowired
    public DiscountGateway(HttpClient client, DiscountServiceProperties discountServiceProperties) {
        this.httpClient = client;
        this.discountServiceProperties = discountServiceProperties;
    }

    @Override
    public IDiscount getDiscount(String voucherCode) throws DownstreamException {
        ObjectMapper objectMapper = new ObjectMapper();
        String path =  "/voucher/" +voucherCode + "?key=" + discountServiceProperties.getApikey();
        Optional<String> correlationId = Optional.ofNullable(MDC.get("x-correlation-id"));

        HttpRequest getRequest = HttpRequest.
                newBuilder(URI.create(discountServiceProperties.getProtocol() + "://"+ discountServiceProperties.getHost() + ":" +discountServiceProperties.getPort() + path)).
                GET()
                .setHeader("Accept",MediaType.APPLICATION_JSON_VALUE)
                .setHeader("x-correlation-id", correlationId.orElse(UUID.randomUUID().toString())).build();
        CompletableFuture<HttpResponse<String>> response =  httpClient.sendAsync(
                getRequest, HttpResponse.BodyHandlers.ofString()
        );
        DiscountDTO discountDTO = null;
        String result = null;
        try {
           result = response.thenApply(HttpResponse::body).get(discountServiceProperties.getTimeout(), TimeUnit.SECONDS);
           discountDTO = objectMapper.readValue(result, DiscountDTO.class);
           if (!ObjectUtils.isEmpty(discountDTO.getError())) {
                log.error("Discount code used {} is not valid | error {}", voucherCode, discountDTO.getError());
               throw new DownstreamException("Discount code isn't valid");
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new DownstreamException("[IO] Calling discount service was interrupted");
        } catch (ExecutionException e) {
            log.error(e.getMessage());
            throw new DownstreamException("[IO] Exception encountered executing discount service api call");
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            throw new DownstreamException("[IO] Timeout calling discount service");
        } catch (JsonMappingException e) {
            log.info("Cant map result from discount service | [result] {}", result);
            log.error(e.getMessage());
        } catch (JsonProcessingException e) {
            log.info("Cant map result from discount service | [result] {}", result);
            log.error(e.getMessage());
        }
        return discountDTO;
    }
}
