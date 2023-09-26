package com.alquimiasoft.com.minegocio.controller;

import com.alquimiasoft.com.minegocio.dto.request.ClientRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.ClientResponseDto;
import com.alquimiasoft.com.minegocio.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private IClientService clientService;
    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> searchClients(
            @RequestParam(required = false) String identity,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(clientService.searchClients(identity, name));
    }

    @PostMapping("/create")
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientRequestDto));
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long clientId, @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto updatedClient = clientService.updateClient(clientId, clientRequestDto);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }
}
