package com.test.masvoz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Provider {

    String proveedor;
    Integer mensajesEnviados;
    List<Prefix> prefixes;
}
