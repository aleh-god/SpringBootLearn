package org.GODevelopment.SimpleWebApp.Service;

import org.GODevelopment.SimpleWebApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Core interface which loads user-specific data. It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider.
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    // Locates the user based on the username. In the actual implementation, the search may possibly be case sensitive, or case insensitive depending on how the implementation instance is configured. In this case, the UserDetails object that comes back may have a username that is of a different case than what was actually requested..
    //Parameters: username - the username identifying the user whose data is required.
    //Returns: a fully populated user record (never null)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
