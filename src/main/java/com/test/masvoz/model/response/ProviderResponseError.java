package com.test.masvoz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProviderResponseError {

    int errorCode;
    String message;

}
