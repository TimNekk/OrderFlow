package timnekk.orderflow.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import timnekk.orderflow.auth.CurrentUserComponent;
import timnekk.orderflow.user.entity.User;
import timnekk.orderflow.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CurrentUserComponent currentUserComponent;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        UserDetails userDetails = currentUserComponent.getCurrentUser();

        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
