package com.test.masvoz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProviderResponse {

    UUID uuid;
    String provider;

}
