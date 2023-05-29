package timnekk.orderflow.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import timnekk.orderflow.auth.service.AuthenticationService;
import timnekk.orderflow.auth.dto.AuthenticationRequest;
import timnekk.orderflow.auth.dto.AuthenticationResponse;
import timnekk.orderflow.auth.dto.RegisterRequest;
import timnekk.orderflow.exception.dto.StringExceptionResponse;
import timnekk.orderflow.exception.dto.StringMapExceptionResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered new user",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = StringMapExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Username already exists",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Received registration request for username: {}", request.getUsername());
        try {
            AuthenticationResponse response = authenticationService.register(request);
            log.info("Registration successful for username: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            log.error("Error occurred while registering user: {}", request.getUsername());
            throw ex;
        }
    }

    @Operation(summary = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated user",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "403", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = StringMapExceptionResponse.class)))
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        log.info("Received authentication request for username: {}", request.getUsername());
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            log.info("Authentication successful for username: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            log.error("Error occurred while authenticating user: {}", request.getUsername());
            throw ex;
        }
    }
}
