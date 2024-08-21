// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
package org.springframework.samples.petclinic.api.application;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.petclinic.api.dto.VetDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class VetsServiceClient {
    @Value("${poc.address.vet:ec2-13-228-75-181.ap-southeast-1.compute.amazonaws.com:8080}")
    private String pocVetsAddress;
    private final WebClient.Builder webClientBuilder;

    public String getPocVetsAddress() {
        return "http://" + pocVetsAddress;
    }

    @WithSpan
    public Flux<VetDetails> getVets() {
        return webClientBuilder.build().get()
                .uri(getPocVetsAddress() + "/vets")
                .retrieve()
                .bodyToFlux(VetDetails.class);
    }
}
