package com.craig.pe.clientservicebank.documents.dto;

import com.craig.pe.clientservicebank.documents.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {
    private String id;
    private String code;
    private String iban;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private ClientType clientType;
}
