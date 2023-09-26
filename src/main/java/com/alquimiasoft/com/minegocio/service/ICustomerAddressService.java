package com.alquimiasoft.com.minegocio.service;

import com.alquimiasoft.com.minegocio.dto.request.CustomerAddressRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.CustomerAddressResponseDto;
import com.alquimiasoft.com.minegocio.model.Client;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;

import java.util.List;

public interface ICustomerAddressService {

    /**
     * returns the primary address of a client
     * @param client
     * @see CustomerAddress
     * @return CustomerAddress
     */
    CustomerAddress getMainAddress(Client client);

    /**
     * Create a customer address
     * @param addressRequestDto
     */
    void createCustomerAddress(CustomerAddressRequestDto addressRequestDto);

    /**
     * get a client's address list
     * @param clientId
     * @see CustomerAddressResponseDto
     * @return List<CustomerAddressResponseDto>
     */
    List<CustomerAddressResponseDto> listClientAddresses(Long clientId);
}

