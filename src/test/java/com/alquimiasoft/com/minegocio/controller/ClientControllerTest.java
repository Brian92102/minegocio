package com.alquimiasoft.com.minegocio.controller;

import com.alquimiasoft.com.minegocio.dto.request.ClientRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.ClientResponseDto;
import com.alquimiasoft.com.minegocio.exceptions.ClientNotFoundException;
import com.alquimiasoft.com.minegocio.exceptions.DuplicateIdentificationException;
import com.alquimiasoft.com.minegocio.exceptions.EmptySearchCriteriaException;
import com.alquimiasoft.com.minegocio.exceptionsHandlers.GlobalExceptionHandler;
import com.alquimiasoft.com.minegocio.fixtures.ClientFixture;
import com.alquimiasoft.com.minegocio.service.IClientService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
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

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ClientControllerTest {
    private MockMvc mockMvc;
    private IClientService clientService = Mockito.mock(IClientService.class);
    private  ClientFixture clientFixture = new ClientFixture();

    private  ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
        mockMvc = MockMvcBuilders.standaloneSetup(new ClientController(clientService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    //region searchClients
    @Test
    public void searchClients() throws Exception {
        String identity = "12345";
        String name = "Brian Perez Riquelme";
        List<ClientResponseDto> expectedResponse = clientFixture.getExpectedClients();

        when(clientService.searchClients(identity, name)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/clients")
                        .param("identity", identity)
                        .param("name", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<ClientResponseDto> responseDtoList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        String StringResponse = mapper.writeValueAsString(responseDtoList);
        String StringExpectedResponse = mapper.writeValueAsString(expectedResponse);
        assert (StringResponse).equals(StringExpectedResponse);
    }

    @Test
    public void searchClientsByIdentity() throws Exception {
        String identity = "12345";
        List<ClientResponseDto> expectedResponse = clientFixture.getExpectedClients();

        when(clientService.searchClients(identity,null)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/clients")
                        .param("identity", identity)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<ClientResponseDto> responseDtoList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        String StringResponse = mapper.writeValueAsString(responseDtoList);
        String StringExpectedResponse = mapper.writeValueAsString(expectedResponse);
        assert (StringResponse).equals(StringExpectedResponse);
    }

    @Test
    public void searchClientsByName() throws Exception {
        String name = "Brian Perez Riquelme";
        List<ClientResponseDto> expectedResponse = clientFixture.getExpectedClients();

        when(clientService.searchClients(null, name)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/clients")
                        .param("name", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<ClientResponseDto> responseDtoList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        String StringResponse = mapper.writeValueAsString(responseDtoList);
        String StringExpectedResponse = mapper.writeValueAsString(expectedResponse);
        assert (StringResponse).equals(StringExpectedResponse);
    }

    @Test
    public void searchClientsWithNullParams() throws Exception {
        EmptySearchCriteriaException exceptionExpected = new EmptySearchCriteriaException(HttpStatus.BAD_REQUEST, EmptySearchCriteriaException.getEmptySearchCriteria());

        when(clientService.searchClients(null, null)).thenThrow(exceptionExpected);

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/clients")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (ServletException ex){
            Throwable rootCause = ex.getRootCause();
            assertTrue(rootCause instanceof EmptySearchCriteriaException);
            assertTrue(rootCause.getMessage().equals(exceptionExpected.getMessage()));
        }
    }
    //endregion

    //region createClient
    @Test
    public void createClient() throws Exception {
        ClientRequestDto body = clientFixture.getClientRequestToCreate();
        ClientResponseDto expectedResponse = clientFixture.getExpectedClients().get(0);
        when(clientService.createClient(any(ClientRequestDto.class))).thenReturn(expectedResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/clients/create")
                        .content(mapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(expectedResponse);

        assertEquals(expectedJson, jsonResponse);
    }

    @Test
    public void createClientDuplicateIdentification() throws Exception {
        ClientRequestDto body = clientFixture.getClientRequestToCreate();
        DuplicateIdentificationException exception = new DuplicateIdentificationException(HttpStatus.BAD_REQUEST, DuplicateIdentificationException.getDuplicateIdentification());
        when(clientService.createClient(any(ClientRequestDto.class))).thenThrow(exception);
        try{
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/clients/create")
                            .content(mapper.writeValueAsString(body))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }catch (ServletException ex){
            Throwable rootCause = ex.getRootCause();
            assertTrue(rootCause instanceof DuplicateIdentificationException);
            assertTrue(rootCause.getMessage().equals(exception.getMessage()));
        }
    }
    //endregion

    //region updateClient
    @Test
    public void updateClient() throws Exception {
        ClientRequestDto body = clientFixture.getClientRequestToUpdate();
        Long id = 1L;
        ClientResponseDto expectedResponse = clientFixture.getExpectedClientUpdated();
        when(clientService.updateClient(eq(id),any(ClientRequestDto.class))).thenReturn(expectedResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/clients/{clientId}",id)
                        .content(mapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(expectedResponse);

        assertEquals(expectedJson, jsonResponse);
    }

    @Test
    public void updateClientDuplicateIdentificationException() throws Exception {
        ClientRequestDto body = clientFixture.getClientRequestToUpdate();
        Long id = 1L;
        DuplicateIdentificationException exception = new DuplicateIdentificationException(HttpStatus.BAD_REQUEST, DuplicateIdentificationException.getDuplicateIdentification());
        when(clientService.updateClient(eq(id),any(ClientRequestDto.class))).thenThrow(exception);
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/clients/{clientId}",id)
                            .content(mapper.writeValueAsString(body))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }catch (ServletException ex){
            Throwable rootCause = ex.getRootCause();
            assertTrue(rootCause instanceof DuplicateIdentificationException);
            assertTrue(rootCause.getMessage().equals(exception.getMessage()));
        }

    }
    //endregion

    //region deleteClient
    @Test
    public void deleteClient() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/clients/{clientId}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(clientService, times(1)).deleteClient(id);
    }

    @Test
    public void deleteClientNotFound() throws Exception {
        Long id = 1L;
        ClientNotFoundException exception = new ClientNotFoundException(HttpStatus.NOT_FOUND, ClientNotFoundException.getClientNotFound());
        doThrow(exception).when(clientService).deleteClient(id);
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/clients/{clientId}",id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (ServletException ex){
            Throwable rootCause = ex.getRootCause();
            assertTrue(rootCause instanceof ClientNotFoundException);
            assertTrue(rootCause.getMessage().equals(exception.getMessage()));
        }
    }
    //endregion
}
