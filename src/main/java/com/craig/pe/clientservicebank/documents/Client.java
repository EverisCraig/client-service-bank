package com.craig.pe.clientservicebank.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {
    @Id
    private String clientId;

    private String clientIdentityType;

    private String clientIdentityNumber;

    private String clientType;

    private String clientName;

    private String clientLastName;

    private String clientEmail;

    private String clientPhone;

    private String clientAddress;

}
