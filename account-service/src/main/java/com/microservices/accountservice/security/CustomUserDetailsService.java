package com.microservices.accountservice.security;

import com.microservices.accountservice.entity.User;
import com.microservices.accountservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        LOGGER.info("[CustomUserDetailsService]loadUserByUsername():  usernameOrEmail, {}", usernameOrEmail);

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        LOGGER.info("[CustomUserDetailsService]loadUserByUsername():  user.getRoles(), {}", user.getRoles());

        Set<GrantedAuthority> authorities = user.getRoles().stream().map(
                (role -> new SimpleGrantedAuthority(role.getName()))   // role.getName()
        ).collect(Collectors.toSet());

        LOGGER.info("[CustomUserDetailsService]loadUserByUsername():  {}, {},  role: {}", user.getEmail(), user.getPassword(), user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
    }
}
