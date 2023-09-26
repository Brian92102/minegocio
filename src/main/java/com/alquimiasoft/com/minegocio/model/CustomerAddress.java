package com.alquimiasoft.com.minegocio.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province", length = 50)
    private String province;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "address", length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "is_primary")
    private boolean isPrimary;
}

