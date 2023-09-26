package com.alquimiasoft.com.minegocio.controller;

import com.alquimiasoft.com.minegocio.dto.request.CustomerAddressRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.CustomerAddressResponseDto;
import com.alquimiasoft.com.minegocio.exceptionsHandlers.GlobalExceptionHandler;
import com.alquimiasoft.com.minegocio.fixtures.CustomerAddressFixture;
import com.alquimiasoft.com.minegocio.service.ICustomerAddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerAddressControllerTest {
    private MockMvc mockMvc;
    private ICustomerAddressService customerAddressService = Mockito.mock(ICustomerAddressService.class);
    private CustomerAddressFixture customerAddressFixture = new CustomerAddressFixture();

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerAddressController(customerAddressService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    //region createCustomerAddress
    @Test
    public void createCustomerAddress() throws Exception {
        CustomerAddressRequestDto body = customerAddressFixture.getCustomerAddressRequest();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/customer-addresses/create")
                        .content(mapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    //endregion

    //region getListClientAddresses
    @Test
    public void getListClientAddresses() throws Exception {
        Long clientId = 1L;
        List<CustomerAddressResponseDto> expectedResult = customerAddressService.listClientAddresses(clientId);
        when(customerAddressService.listClientAddresses(clientId)).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/customer-addresses/list")
                .param("clientId", String.valueOf(clientId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(expectedResult);
        assertEquals(expectedJson, jsonResponse);

    }
    //endregion
}
