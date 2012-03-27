package com.zenjava.firstcontact.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SecurityServiceImpl implements SecurityService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Inject private AuthenticationManager authenticationManager;

    public void login(String userName, String password)
    {
        // never log password information!
        log.info("Logging in user '{}'", userName);
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("User '{}' successfully logged in with authorities: '{}'", userName, authentication.getAuthorities());
    }
}
