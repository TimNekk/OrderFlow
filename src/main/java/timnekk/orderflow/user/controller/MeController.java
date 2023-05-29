package timnekk.orderflow.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timnekk.orderflow.exception.dto.StringExceptionResponse;
import timnekk.orderflow.exception.dto.StringMapExceptionResponse;
import timnekk.orderflow.user.dto.UserDTO;
import timnekk.orderflow.user.entity.User;
import timnekk.orderflow.user.mapper.UserMapper;
import timnekk.orderflow.user.service.UserService;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class MeController {

    private final UserService userService;

    @Operation(summary = "Get current authenticated user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user's information",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "403", description = "User is not authenticated",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @GetMapping
    public ResponseEntity<UserDTO> getMe() {
        User user = userService.getCurrentUser();
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

}
