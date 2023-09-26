package com.alquimiasoft.com.minegocio.service.impl;

import com.alquimiasoft.com.minegocio.dto.mapper.CustomerAddressResponseMapper;
import com.alquimiasoft.com.minegocio.dto.request.ClientRequestDto;
import com.alquimiasoft.com.minegocio.dto.request.CustomerAddressRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.CustomerAddressResponseDto;
import com.alquimiasoft.com.minegocio.model.Client;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;
import com.alquimiasoft.com.minegocio.repository.CustomerAddressRepository;
import com.alquimiasoft.com.minegocio.service.ICustomerAddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerAddressService implements ICustomerAddressService {

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    //region createCustomerAddress
    @Transactional
    public void createCustomerAddress(CustomerAddressRequestDto addressRequestDto) {
        CustomerAddress newAddress = new CustomerAddress();
        newAddress.setProvince(addressRequestDto.getProvince());
        newAddress.setCity(addressRequestDto.getCity());
        newAddress.setAddress(addressRequestDto.getAddress());
        customerAddressRepository.save(newAddress);
    }
    //endregion

    //region listClientAddresses
    public List<CustomerAddressResponseDto> listClientAddresses(Long clientId) {
        List<CustomerAddress> addresses = customerAddressRepository.findByClientId(clientId);
        List<CustomerAddressResponseDto> addressDtos = addresses.stream()
                .map(address-> CustomerAddressResponseMapper.mapToResponseDto(address))
                .collect(Collectors.toList());
        return addressDtos;
    }
    //endregion

    public CustomerAddress getMainAddress(Client client) {
        for (CustomerAddress address : client.getAddresses()) {
            if (address.isPrimary()) {
                return address;
            }
        }
        return null;
    }

    public CustomerAddress buildCustomerAddresService(ClientRequestDto clientRequestDto, Client newClient){
        CustomerAddress mainAddress = new CustomerAddress();
        mainAddress.setProvince(clientRequestDto.getProvince());
        mainAddress.setCity(clientRequestDto.getCity());
        mainAddress.setAddress(clientRequestDto.getAddress());
        mainAddress.setClient(newClient);
        mainAddress.setPrimary(true);
        return mainAddress;
    }
}
