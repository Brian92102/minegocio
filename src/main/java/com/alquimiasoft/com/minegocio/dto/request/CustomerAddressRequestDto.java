package com.alquimiasoft.com.minegocio.dto.request;

import com.alquimiasoft.com.minegocio.model.Client;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CustomerAddressRequestDto {
    private String province;
    private String city;
    private String address;
    private Client client;
    private boolean isPrimary;
}

