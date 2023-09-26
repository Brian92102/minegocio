package com.alquimiasoft.com.minegocio.controller;

import com.alquimiasoft.com.minegocio.dto.request.CustomerAddressRequestDto;
import com.alquimiasoft.com.minegocio.dto.response.CustomerAddressResponseDto;
import com.alquimiasoft.com.minegocio.service.ICustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-addresses")
public class CustomerAddressController {

    private ICustomerAddressService customerAddressService;
    @Autowired
    public CustomerAddressController(ICustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomerAddress(@RequestBody CustomerAddressRequestDto addressRequestDto) {
        customerAddressService.createCustomerAddress(addressRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<CustomerAddressResponseDto>> listClientAddresses(@RequestParam Long clientId) {
        return  ResponseEntity.ok(customerAddressService.listClientAddresses(clientId));
    }
}
