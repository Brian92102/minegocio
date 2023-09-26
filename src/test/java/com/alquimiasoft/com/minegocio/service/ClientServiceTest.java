package com.alquimiasoft.com.minegocio.service;


import com.alquimiasoft.com.minegocio.dto.request.ClientRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.ClientResponseDto;
import com.alquimiasoft.com.minegocio.exceptions.ClientNotFoundException;
import com.alquimiasoft.com.minegocio.exceptions.DuplicateIdentificationException;
import com.alquimiasoft.com.minegocio.exceptions.EmptySearchCriteriaException;
import com.alquimiasoft.com.minegocio.fixtures.CustomerAddressFixture;
import com.alquimiasoft.com.minegocio.fixtures.ClientFixture;
import com.alquimiasoft.com.minegocio.model.Client;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;
import com.alquimiasoft.com.minegocio.repository.ClientRepository;
import com.alquimiasoft.com.minegocio.service.impl.ClientService;
import com.alquimiasoft.com.minegocio.service.impl.CustomerAddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CustomerAddressService customerAddressService;

    private ClientFixture clientFixture = new ClientFixture();
    private CustomerAddressFixture customerAddressFixture = new CustomerAddressFixture();

    private ObjectMapper mapper = new ObjectMapper();


    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    //region searchClients
    @Test
    public void searchClientsByIdentityAndName() throws JsonProcessingException {
        String identity = "12345";
        String name = "Brian Perez Riquelme";
        List<Client> clientList = clientFixture.getClients();
        List<ClientResponseDto> expectedResult = clientFixture.getExpectedClients();
        when(clientRepository.findByIdentificationNumberAndNames(identity, name)).thenReturn(clientList);
        when(customerAddressService.getMainAddress(any())).thenReturn(customerAddressFixture.getMainAddress());
        List<ClientResponseDto> clientListResult = clientService.searchClients(identity, name);
        assertEquals(mapper.writeValueAsString(expectedResult),(mapper.writeValueAsString(clientListResult)));
    }

    @Test
    public void searchClientsByIdentity() throws JsonProcessingException {
        String identity = "12345";
        List<Client> clientList = clientFixture.getClients();
        List<ClientResponseDto> expectedResult = clientFixture.getExpectedClients();
        when(clientRepository.findByIdentificationNumber(identity)).thenReturn(clientList);
        when(customerAddressService.getMainAddress(any())).thenReturn(customerAddressFixture.getMainAddress());
        List<ClientResponseDto> clientListResult = clientService.searchClients(identity, "");
        assertEquals(mapper.writeValueAsString(expectedResult),(mapper.writeValueAsString(clientListResult)));
    }

    @Test
    public void searchClientsByName() throws JsonProcessingException {
        String name = "Brian Perez Riquelme";
        List<Client> clientList = clientFixture.getClients();
        List<ClientResponseDto> expectedResult = clientFixture.getExpectedClients();
        when(clientRepository.findByNames(name)).thenReturn(clientList);
        when(customerAddressService.getMainAddress(any())).thenReturn(customerAddressFixture.getMainAddress());
        List<ClientResponseDto> clientListResult = clientService.searchClients("", name);
        assertEquals(mapper.writeValueAsString(expectedResult),(mapper.writeValueAsString(clientListResult)));
    }

    @Test(expected = EmptySearchCriteriaException.class)
    public void searchClientsNullParams() {
        clientService.searchClients("", "");
    }

    @Test
    public void searchClientsByIdentityAndNameNotFound() {
        String identity = "241242";
        String name = "Juan Perez";
        when(clientRepository.findByIdentificationNumberAndNames(identity, name)).thenReturn(Collections.emptyList());
        when(customerAddressService.getMainAddress(any())).thenReturn(customerAddressFixture.getMainAddress());
        List<ClientResponseDto> clientListResult = clientService.searchClients(identity, name);
        assert(clientListResult.isEmpty());
    }
    //endregion

    //region createClient
    @Test
    public void createClient() throws JsonProcessingException {
        ClientRequestDto clientRequestDto = clientFixture.getClientRequestToCreate();
        CustomerAddress mainAddress = customerAddressFixture.getMainAddress();
        ClientResponseDto expectedResponse = clientFixture.getExpectedClients().get(0);
        Client clientCreated = clientFixture.getClientCreated(mainAddress);
        when(clientRepository.save(any(Client.class))).thenReturn(clientCreated);
        when(customerAddressService.buildCustomerAddresService(eq(clientRequestDto),any(Client.class))).thenReturn(mainAddress);
        ClientResponseDto result = clientService.createClient(clientRequestDto);
        assert(mapper.writeValueAsString(result)).equals(mapper.writeValueAsString(expectedResponse));
    }

    @Test(expected = DuplicateIdentificationException.class)
    public void createClientWithDuplicateIdentification() throws JsonProcessingException {
        ClientRequestDto clientRequestDto = clientFixture.getClientRequestToCreate();
        when(clientRepository.existsByIdentificationNumber(clientRequestDto.getIdentificationNumber())).thenReturn(true);
        clientService.createClient(clientRequestDto);
    }
    //endregion

    //region updateClient
    @Test
    public void updateClientOk() throws JsonProcessingException {
        Long clientId = 1L;
        ClientRequestDto clientRequestDto = clientFixture.getClientRequestToUpdate();
        Client existingClient = clientFixture.getClients().get(0);
        ClientResponseDto updatedClient = clientFixture.getExpectedClientUpdated();
        when(clientRepository.findById(clientId)).thenReturn(Optional.ofNullable(existingClient));
        when(clientRepository.save(existingClient)).thenReturn(existingClient);
        when(customerAddressService.getMainAddress(any())).thenReturn(customerAddressFixture.getMainAddress());
        ClientResponseDto result = clientService.updateClient(clientId, clientRequestDto);
        assertEquals (mapper.writeValueAsString(result),mapper.writeValueAsString(updatedClient));
    }

    @Test(expected = ClientNotFoundException.class)
    public void updateClientNotFound() throws JsonProcessingException {
        Long clientId = 1L;
        ClientRequestDto clientRequestDto = clientFixture.getClientRequestToUpdate();
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        clientService.updateClient(clientId,clientRequestDto);
    }

    @Test(expected = DuplicateIdentificationException.class)
    public void updateClientDuplicateIdentificationException() throws JsonProcessingException {
        Long clientId = 1L;
        ClientRequestDto clientRequestDto = clientFixture.getClientRequestToUpdate();
        Client existingClient = clientFixture.getClients().get(0);
        when(clientRepository.findById(clientId)).thenReturn(Optional.ofNullable(existingClient));
        when(clientRepository.existsByIdentificationNumber(clientRequestDto.getIdentificationNumber())).thenReturn(true);
        clientService.updateClient(clientId, clientRequestDto);
    }
    //endregion

    //region delete clients
    @Test
    public void deleteClient() {
        Long clientId = 1L;
        Client existingClient = clientFixture.getClients().get(0);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        clientService.deleteClient(clientId);
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).delete(existingClient);
    }

    @Test(expected = ClientNotFoundException.class)
    public void deleteClientClientNotFound() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        clientService.deleteClient(clientId);
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, never()).delete(any());
    }
    //endregion

}
