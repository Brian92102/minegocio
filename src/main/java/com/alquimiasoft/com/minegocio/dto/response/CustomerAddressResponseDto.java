package com.alquimiasoft.com.minegocio.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAddressResponseDto {
    private Long id;
    private String province;
    private String city;
    private String address;
    private boolean isPrimary;
}
