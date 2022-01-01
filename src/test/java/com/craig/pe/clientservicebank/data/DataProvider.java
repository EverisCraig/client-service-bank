package com.craig.pe.clientservicebank.data;

import com.craig.pe.clientservicebank.documents.Client;

public class DataProvider {
    public static Client clientOne() {
        return Client.builder()
                .clientId("1")
                .clientIdentityType("DNI")
                .clientIdentityNumber("12345678")
                .clientType("PERSONAL")
                .clientName("Craig")
                .clientLastName("Castro")
                .clientEmail("thetai6102@gmail.com")
                .clientPhone("+56987654321")
                .clientAddress("Av. Wiesse 123")
                .build();
    }

    public static Client clientTwo() {
        return Client.builder()
                .clientId("2")
                .clientIdentityType("DNI")
                .clientIdentityNumber("12345678")
                .clientType("PERSONAL")
                .clientName("Rick")
                .clientLastName("Julcamoro")
                .clientEmail("")
                .clientPhone("+56987654321")
                .clientAddress("Av. Wiesse 123")
                .build();
    }

    public static Client clientThree() {
        return Client.builder()
                .clientId("3")
                .clientIdentityType("DNI")
                .clientIdentityNumber("12345678")
                .clientType("PERSONAL")
                .clientName("Carlos")
                .clientLastName("Castro")
                .clientEmail("")
                .clientPhone("+56987654321")
                .clientAddress("Av. Wiesse 123")
                .build();
    }

}
