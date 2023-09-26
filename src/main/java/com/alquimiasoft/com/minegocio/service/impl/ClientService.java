package com.alquimiasoft.com.minegocio.service.impl;

import com.alquimiasoft.com.minegocio.dto.request.ClientRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.ClientResponseDto;
import com.alquimiasoft.com.minegocio.dto.mapper.ClientResponseMapper;
import com.alquimiasoft.com.minegocio.exceptions.ClientNotFoundException;
import com.alquimiasoft.com.minegocio.exceptions.DuplicateIdentificationException;
import com.alquimiasoft.com.minegocio.exceptions.EmptySearchCriteriaException;
import com.alquimiasoft.com.minegocio.model.Client;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;
import com.alquimiasoft.com.minegocio.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements com.alquimiasoft.com.minegocio.service.IClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CustomerAddressService customerAddressService;

    //region searchClients
    public List<ClientResponseDto> searchClients(String identity, String name){
        List<Client> clientsFound = getClientsByIdentityOrName(identity, name);
        return clientsFound.stream()
                .map(client -> ClientResponseMapper.mapToDto(client, customerAddressService.getMainAddress(client)))
                .collect(Collectors.toList());
    }

    private List<Client> getClientsByIdentityOrName(String identity, String name) {
        if (hasIdentityAndName(identity, name)) return clientRepository.findByIdentificationNumberAndNames(identity, name);
        if (hasIdentity(identity)) return clientRepository.findByIdentificationNumber(identity);
        if (hasName(name)) return clientRepository.findByNames(name);
        throw new EmptySearchCriteriaException(HttpStatus.BAD_REQUEST, EmptySearchCriteriaException.getEmptySearchCriteria());
    }

    private boolean hasIdentityAndName(String identity, String name) {
        return (identity != null && !identity.isBlank() && name != null && !name.isBlank());
    }

    private boolean hasIdentity(String identity) {
        return identity != null && !identity.isBlank();
    }

    private boolean hasName(String name) {
        return name != null && !name.isBlank();
    }
    //endregion

    //region createClient
    @Transactional
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto){

        checkForDuplicateIdentification(clientRequestDto.getIdentificationNumber());

        Client newClient = buildClient(new Client(), clientRequestDto);
        CustomerAddress mainAddress = customerAddressService.buildCustomerAddresService(clientRequestDto,newClient);
        newClient.getAddresses().add(mainAddress);
        Client savedClient = clientRepository.save(newClient);

       return ClientResponseMapper.mapToDto(savedClient,mainAddress);
    }
    //endregion

    //region updateClient
    @Transactional
    public ClientResponseDto updateClient(Long clientId, ClientRequestDto clientRequestDto) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(HttpStatus.NOT_FOUND, ClientNotFoundException.getClientNotFound()));

        checkForDuplicateIdentification(existingClient,clientRequestDto);

        existingClient = buildClient(existingClient, clientRequestDto);

        Client updatedClient = clientRepository.save(existingClient);

        return ClientResponseMapper.mapToDto(updatedClient, customerAddressService.getMainAddress(updatedClient));
    }

    //endregion

    //region delete clients
    @Transactional
    public void deleteClient(Long clientId) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(HttpStatus.NOT_FOUND, ClientNotFoundException.getClientNotFound()));
        clientRepository.delete(existingClient);
    }
    //endregion

    private Client buildClient(Client client, ClientRequestDto clientRequestDto){
        client.setIdentificationType(clientRequestDto.getIdentificationType());
        client.setIdentificationNumber(clientRequestDto.getIdentificationNumber());
        client.setNames(clientRequestDto.getNames());
        client.setEmail(clientRequestDto.getEmail());
        client.setPhoneNumber(clientRequestDto.getPhoneNumber());
        return client;
    }

    private void checkForDuplicateIdentification(String identificationNumber) {
        if (clientRepository.existsByIdentificationNumber(identificationNumber)) {
            throw new DuplicateIdentificationException(HttpStatus.BAD_REQUEST, DuplicateIdentificationException.getDuplicateIdentification());
        }
    }

    private void checkForDuplicateIdentification(Client existingClient, ClientRequestDto clientRequestDto) {
        if (!existingClient.getIdentificationNumber().equals(clientRequestDto.getIdentificationNumber()) &&
                clientRepository.existsByIdentificationNumber(clientRequestDto.getIdentificationNumber())) {
            throw new DuplicateIdentificationException(HttpStatus.BAD_REQUEST, DuplicateIdentificationException.getDuplicateIdentification());
        }
    }


}
