package com.alquimiasoft.com.minegocio.dto.mapper;

import com.alquimiasoft.com.minegocio.dto.response.ClientResponseDto;
import com.alquimiasoft.com.minegocio.model.Client;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;

public class ClientResponseMapper {

    public static ClientResponseDto mapToDto(Client client, CustomerAddress mainAddress) {
        ClientResponseDto dto = new ClientResponseDto();
        dto.setId(client.getId());
        dto.setIdentificationType(client.getIdentificationType());
        dto.setIdentificationNumber(client.getIdentificationNumber());
        dto.setNames(client.getNames());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setProvince(mainAddress.getProvince());
        dto.setCity(mainAddress.getCity());
        dto.setAddress(mainAddress.getAddress());
        return dto;
    }
}
