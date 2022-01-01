package com.craig.pe.clientservicebank.configuration;

import com.craig.pe.clientservicebank.documents.Client;
import com.craig.pe.clientservicebank.handler.ClientHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {
    @RouterOperations({
            @RouterOperation(
                    path = "/clients",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ClientHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAll",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Client.class
                                                    ))
                                    )
                            }
                    )
            ),

            @RouterOperation(
                    path = "/clients/{clientId}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ClientHandler.class,
                    beanMethod = "findById",
                    operation = @Operation(
                            operationId = "findById",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Client.class
                                                    ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "client not found with the given id"
                                    )
                            },
                            parameters = {
                                    @Parameter(
                                            in = ParameterIn.PATH,
                                            name = "clientId"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/clients/name/{clientName}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ClientHandler.class,
                    beanMethod = "findByClientName",
                    operation = @Operation(
                            operationId = "findByClientName",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Client.class
                                                    ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404", description = "client not found with the given clientIdentityNumber"
                                    )
                            },
                            parameters = {
                                    @Parameter(
                                            in = ParameterIn.PATH,
                                            name = "clientName"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/clients/identity/{clientIdentityNumber}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ClientHandler.class,
                    beanMethod = "findByClientIdentityNumber",
                    operation = @Operation(
                            operationId = "findByClientIdentityNumber",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Client.class
                                                    ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404", description = "client not found with the given clientIdentityNumber"
                                    )
                            },
                            parameters = {
                                    @Parameter(
                                            in = ParameterIn.PATH,
                                            name = "clientIdentityNumber"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/clients",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.POST,
                    beanClass = ClientHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = "create",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = String.class
                                                    ))
                                    ),
                            },
                            requestBody = @RequestBody(
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Client.class
                                            ))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/clients",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.PUT,
                    beanClass = ClientHandler.class,
                    beanMethod = "update",
                    operation = @Operation(
                            operationId = "update",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = String.class
                                                    ))
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Client.class
                                            )
                                    )
                            )
                    )
            ),
            @RouterOperation(
                    path = "/clients/{clientId}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.DELETE,
                    beanClass = ClientHandler.class,
                    beanMethod = "delete",
                    operation = @Operation(
                            operationId = "delete",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = String.class
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid Client ID supplied"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Employee not found"
                                    )
                            },
                            parameters = {

                                    @Parameter(
                                            in = ParameterIn.PATH,
                                            name = "clientId"

                                    )}
                    )),
    })
    @Bean
    public RouterFunction<ServerResponse> routes(ClientHandler clientHandler) {
        return route(GET("/clients/all"), request -> clientHandler.findAll())
                .andRoute(GET("/clients/{clientId}"), clientHandler::findById)
                .andRoute(GET("/clients/name/{clientName}"), clientHandler::findByClientName)
                .andRoute(GET("/clients/identity/{clientIdentityNumber}"), clientHandler::findByClientIdentityNumber)
                .andRoute(POST("/clients"), clientHandler::create)
                .andRoute(DELETE("/clients/{clientId}"), clientHandler::delete)
                .andRoute(PUT("/clients"), clientHandler::update);
    }

}
