package br.com.lufamador.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.User;
import br.com.lufamador.security.jwt.JwtAuthenticationRequest;
import br.com.lufamador.security.jwt.JwtTokenUtil;
import br.com.lufamador.security.model.CurrentUser;
import br.com.lufamador.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException, Exception {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = this.jwtTokenUtil.generateToken(userDetails);
        final User user = this.userService.findByEmail(authenticationRequest.getEmail());
        user.setPassword(null);

        return ResponseEntity.ok(new CurrentUser(token,
                user));
    }

    @PostMapping(value = "/api/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        String username = this.jwtTokenUtil.getUserNameFromToken(token);
        final User user = this.userService.findByEmail(username);

        if (this.jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshToken = this.jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new CurrentUser(refreshToken,
                    user));
        } else {
            return ResponseEntity.badRequest()
                    .body(null);
        }

    }

}

