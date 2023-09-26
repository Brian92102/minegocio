package com.alquimiasoft.com.minegocio.repository;

import com.alquimiasoft.com.minegocio.model.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

    CustomerAddress save(CustomerAddress customerAddress);

    List<CustomerAddress> findByClientId(Long clientId);

}
