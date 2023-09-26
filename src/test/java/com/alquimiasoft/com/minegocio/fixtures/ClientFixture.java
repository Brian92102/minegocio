package com.alquimiasoft.com.minegocio.fixtures;

import com.alquimiasoft.com.minegocio.dto.request.ClientRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.ClientResponseDto;
import com.alquimiasoft.com.minegocio.model.Client;
import com.alquimiasoft.com.minegocio.model.CustomerAddress;

import java.util.Arrays;
import java.util.List;

public class ClientFixture {

    public List<Client> getClients(){
        Client client1 = new Client();
        client1.setId(1L);
        client1.setIdentificationType("ID");
        client1.setIdentificationNumber("12345");
        client1.setNames("Brian Perez Riquelme");
        client1.setEmail("brian@gmail.com");
        return Arrays.asList(client1);
    }

    public List<ClientResponseDto> getExpectedClients(){
        ClientResponseDto dto = new ClientResponseDto();
        dto.setId(1L);
        dto.setIdentificationType("ID");
        dto.setIdentificationNumber("12345");
        dto.setNames("Brian Perez Riquelme");
        dto.setProvince("Main Province");
        dto.setCity("Main City");
        dto.setAddress("Main Street 123");
        dto.setEmail("brian@gmail.com");
        return Arrays.asList(dto);
    }

    public ClientRequestDto getClientRequestToCreate(){
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setIdentificationType("ID");
        clientRequestDto.setIdentificationNumber("12345");
        clientRequestDto.setNames("Brian Perez Riquelme");
        clientRequestDto.setProvince("Main Province");
        clientRequestDto.setCity("Main City");
        clientRequestDto.setAddress("Main Street 123");
        clientRequestDto.setEmail("brian@gmail.com");
        return clientRequestDto;
    }

    public ClientResponseDto getExpectedClientUpdated(){
        ClientResponseDto dto = new ClientResponseDto();
        dto.setId(1L);
        dto.setIdentificationType("ID");
        dto.setIdentificationNumber("123452152151");
        dto.setNames("Brian Perez Riquelme");
        dto.setProvince("Main Province");
        dto.setCity("Main City");
        dto.setAddress("Main Street 123");
        dto.setEmail("brian.perez@gmail.com");
        return dto;
    }

    public ClientRequestDto getClientRequestToUpdate(){
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setIdentificationType("ID");
        clientRequestDto.setIdentificationNumber("123452152151");
        clientRequestDto.setNames("Brian Perez Riquelme");
        clientRequestDto.setProvince("Main Province");
        clientRequestDto.setCity("Main City");
        clientRequestDto.setAddress("Main Street 123");
        clientRequestDto.setEmail("brian.perez@gmail.com");
        return clientRequestDto;
    }

    public Client getClientCreated(CustomerAddress mainAddress){
        Client newClient = new Client();
        newClient.setId(1L);
        newClient.setIdentificationType("ID");
        newClient.setIdentificationNumber("12345");
        newClient.setNames("Brian Perez Riquelme");
        newClient.getAddresses().add(mainAddress);
        newClient.setEmail("brian@gmail.com");
        return newClient;
    }

}
