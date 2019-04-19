package com.zzd.spring.oauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private String failureUrl = "/signIn";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        String username = request.getParameter("username");
        log.debug(username + " try to login");

        response.sendRedirect(failureUrl + "?authentication_error=true&error=loginError");
            /*super.onAuthenticationFailure(request, response, exception);*/
    }
}
