package com.alquimiasoft.com.minegocio.repository;

import com.alquimiasoft.com.minegocio.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByIdentificationNumberAndNames(String identificationNumber, String names);
    List<Client> findByIdentificationNumber(String identificationNumber);
    List<Client> findByNames(String names);
    boolean existsByIdentificationNumber(String identificationNumber);
}
