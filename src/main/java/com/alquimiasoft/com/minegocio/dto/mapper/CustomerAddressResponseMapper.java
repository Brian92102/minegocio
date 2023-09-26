package com.alquimiasoft.com.minegocio.dto.mapper;

import com.alquimiasoft.com.minegocio.dto.response.CustomerAddressResponseDto;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;

public class CustomerAddressResponseMapper {

    public static CustomerAddressResponseDto mapToResponseDto(CustomerAddress customerAddress) {
        CustomerAddressResponseDto dto = new CustomerAddressResponseDto();
        dto.setId(customerAddress.getId());
        dto.setProvince(customerAddress.getProvince());
        dto.setCity(customerAddress.getCity());
        dto.setAddress(customerAddress.getAddress());
        return dto;
    }
}
