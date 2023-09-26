package com.alquimiasoft.com.minegocio.service;

import com.alquimiasoft.com.minegocio.dto.request.ClientRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.ClientResponseDto;

import java.util.List;

public interface IClientService {

    /**
     * Find a list of clients with their primary address
     * @param identity
     * @param name
     * @see ClientResponseDto
     * @return List<ClientResponseDto>
     */
    List<ClientResponseDto> searchClients(String identity, String name);

    /**
     * Service that creates a client along with its main address
     * @param clientRequestDto
     * @see ClientResponseDto
     * @return ClientResponseDto
     */
    ClientResponseDto createClient(ClientRequestDto clientRequestDto);

    /**
     * Service that updates customer data
     * @param clientId
     * @param clientRequestDto
     * @see ClientResponseDto
     * @return
     */
    ClientResponseDto updateClient(Long clientId, ClientRequestDto clientRequestDto);

    /**
     * Service that deletes a client by ID
     * @param clientId
     */
    void deleteClient(Long clientId);
}
