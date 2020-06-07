package com.test.masvoz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class ProviderDto implements Comparable<ProviderDto>, Serializable {

    String proveedor;
    Integer sentMessages;
    Integer coste;

    @Override
    public int compareTo(ProviderDto o) {
        return this.getSentMessages() - o.getSentMessages();
    }

}
