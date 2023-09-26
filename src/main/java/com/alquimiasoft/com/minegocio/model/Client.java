package com.alquimiasoft.com.minegocio.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification_type", nullable = false, length = 6)
    private String identificationType;

    @Column(name = "identification_number", nullable = false, unique = true, length = 15)
    private String identificationNumber;

    @Column(name = "names", nullable = false, length = 50)
    private String names;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerAddress> addresses = new ArrayList<>();

}


