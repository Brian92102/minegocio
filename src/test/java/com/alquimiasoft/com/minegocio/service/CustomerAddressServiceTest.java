package com.alquimiasoft.com.minegocio.service;


import com.alquimiasoft.com.minegocio.dto.request.CustomerAddressRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.CustomerAddressResponseDto;
import com.alquimiasoft.com.minegocio.fixtures.CustomerAddressFixture;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;
import com.alquimiasoft.com.minegocio.repository.CustomerAddressRepository;
import com.alquimiasoft.com.minegocio.service.impl.CustomerAddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CustomerAddressServiceTest {

    @InjectMocks
    private CustomerAddressService customerAddressService;
    @Mock
    private CustomerAddressRepository customerAddressRepository;

    private CustomerAddressFixture customerAddressFixture = new CustomerAddressFixture();

    private ObjectMapper mapper = new ObjectMapper();


    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    //region createCustomerAddress
    @Test
    public void createCustomerAddress() {
        CustomerAddressRequestDto addressRequestDto = customerAddressFixture.getCustomerAddressRequest();
        CustomerAddress customerAddressCreated = customerAddressFixture.getCreateCustomerAddress();
        when(customerAddressRepository.save(any(CustomerAddress.class))).thenReturn(customerAddressCreated);
        customerAddressService.createCustomerAddress(addressRequestDto);

        verify(customerAddressRepository, times(1)).save(any(CustomerAddress.class));
    }
    //endregion

    //region getListClientAddresses
    @Test
    public void getListClientAddresses() {
        Long clientId = 1L;
        List<CustomerAddress> address = customerAddressFixture.getListCustomerAddress();
        when(customerAddressRepository.findByClientId(clientId)).thenReturn(address);
        List<CustomerAddressResponseDto> result = customerAddressService.listClientAddresses(clientId);

        verify(customerAddressRepository, times(1)).findByClientId(clientId);
        assert(address.size() == (result.size()));
    //endregion

}
}
