package com.mynt.logistics.controller;

import com.mynt.logistics.domain.Parcel;
import com.mynt.logistics.integration.DownstreamException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;


@Slf4j
@RequestMapping(path = "/parcel")
@RestController
public class ParcelController {

    private Parcel parcel;

    @Autowired
    public ParcelController(Parcel parcel) {
        this.parcel = parcel;
    }

    @Operation(summary = "This api will accept length , width, height, weight to compute the corresponding cost based on the classification of a parcel.A discount code can be applied to the cost of this parcel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classified parcel and compute corresponding cost",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParcelResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "one of the arguments (weight, height, length, width, voucher) not valid",
                    content = @Content),
            @ApiResponse(responseCode = "424", description = "Communication failure o the downstream service(s)",
                    content = @Content) })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelResponseDTO> calculateParcel(@Valid @RequestBody ParcelMetricsDTO parcelMetricsDTO) throws DownstreamException, ParseException {
        log.debug(parcelMetricsDTO.toString());
        parcel.setParcelMetricsDTO(parcelMetricsDTO);
        Optional<Double> cost = Optional.ofNullable(parcel.calculateCost());
        return ResponseEntity.ok(ParcelResponseDTO.builder().cost(cost.orElse(0.0)).weightClassification(parcel.getWeightClassification()).build());
  }
}