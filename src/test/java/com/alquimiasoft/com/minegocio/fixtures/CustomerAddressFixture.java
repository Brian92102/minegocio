package com.alquimiasoft.com.minegocio.fixtures;

import com.alquimiasoft.com.minegocio.dto.request.CustomerAddressRequestDto;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;

import java.util.Arrays;
import java.util.List;

public class CustomerAddressFixture {
    public CustomerAddress getMainAddress(){
        CustomerAddress mainAddress = new CustomerAddress();
        mainAddress.setProvince("Main Province");
        mainAddress.setCity("Main City");
        mainAddress.setAddress("Main Street 123");
        mainAddress.setPrimary(true);
        return mainAddress;
    }

    public CustomerAddressRequestDto getCustomerAddressRequest(){
        CustomerAddressRequestDto addressRequestDto = new CustomerAddressRequestDto();
        addressRequestDto.setProvince("Test Province");
        addressRequestDto.setCity("Test City");
        addressRequestDto.setAddress("Test Address");
        return addressRequestDto;
    }

    public CustomerAddress getCreateCustomerAddress(){
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setProvince("Test Province");
        customerAddress.setCity("Test City");
        customerAddress.setAddress("Test Address");
        return customerAddress;
    }

    public List<CustomerAddress> getListCustomerAddress(){
        CustomerAddress address1 = new CustomerAddress();
        address1.setProvince("Main Province");
        address1.setCity(" City");
        address1.setAddress("Street 123");
        address1.setPrimary(true);

        CustomerAddress address2 = new CustomerAddress();
        address2.setProvince("Main Province");
        address2.setCity(" City");
        address2.setAddress("Street 123");
        address2.setPrimary(true);

        return Arrays.asList(address1,address2);

    }
}
