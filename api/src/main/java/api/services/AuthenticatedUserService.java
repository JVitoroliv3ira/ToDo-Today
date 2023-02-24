package api.services;

import api.dtos.DetailsDTO;
import api.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

    public static Long getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }
    public static User getAuthenticatedUser() {
        return getAuthenticatedUserDetails().getUser();
    }
    public static DetailsDTO getAuthenticatedUserDetails() {
        return (DetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
