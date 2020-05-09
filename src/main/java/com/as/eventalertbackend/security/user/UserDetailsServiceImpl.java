package com.as.eventalertbackend.security.user;

import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userService.findByEmail(email);
            return UserDetailsImpl.build(user);
        } catch (RecordNotFoundException e) {
            throw new UsernameNotFoundException(email);
        }
    }

}
