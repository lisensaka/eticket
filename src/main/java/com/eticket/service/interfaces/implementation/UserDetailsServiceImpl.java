package com.eticket.service.interfaces.implementation;

import com.eticket.repository.IUserRepository;
import com.eticket.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserService {

    private final IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findUserByEmailEqualsIgnoreCase(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Username: %s , not found", username))
                );
    }
}
