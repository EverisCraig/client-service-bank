package com.craig.pe.clientservicebank.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientType")
@Data
public class ClientType {
    @Id
    private String clientTypeId;
    private String clientTypeName;
}
