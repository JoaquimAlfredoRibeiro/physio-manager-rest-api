package pt.home.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.home.payload.UserIdentityAvailability;
import pt.home.payload.UserSummary;
import pt.home.repositories.UserRepository;
import pt.home.security.CurrentUser;
import pt.home.security.UserPrincipal;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/api/user";

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = UserSummary.builder()
                .id(currentUser.getId())
                .username(currentUser.getUsername())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .build();

        return userSummary;
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }
}
