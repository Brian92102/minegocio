package com.alquimiasoft.com.minegocio.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("identificationType")
    private String identificationType;

    @JsonProperty("identificationNumber")
    private String identificationNumber;

    @JsonProperty("names")
    private String names;

    @JsonProperty("email")
    private String email;

    @JsonProperty("cellphone")
    private String phoneNumber;

    @JsonProperty("mainProvince")
    private String province;

    @JsonProperty("mainCity")
    private String city;

    @JsonProperty("mainAddress")
    private String address;
}
