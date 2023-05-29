package timnekk.orderflow.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timnekk.orderflow.user.dto.UserDTO;
import timnekk.orderflow.user.entity.User;
import timnekk.orderflow.user.mapper.UserMapper;
import timnekk.orderflow.user.service.UserService;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class MeController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getMe() {
        User user = userService.getCurrentUser();
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

}
