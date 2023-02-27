package com.gconsumer.GConsumer.authService;

import com.gconsumer.GConsumer.model.UserCredential;
import com.gconsumer.GConsumer.repository.UserCredentialRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialRepo repository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Load username in userDetailsService {}", username);

        final Optional<UserCredential> optional = this.repository.findByEmailOrPhone(username);
        if (!optional.isPresent()) {
            logger.error("username not found in db in userDetailsService ");
            throw new UsernameNotFoundException("No user name found " + username);
        }
//        if (optional.get().getAuthMethod() == "email") {
//            System.out.println();
//        }
        logger.debug("check is username present in db in userDetailsService {}", optional.get().getFullName());
        return new UserDetailsEx(optional.get());
    }
}
