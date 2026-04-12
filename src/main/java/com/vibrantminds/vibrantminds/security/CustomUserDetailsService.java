package com.vibrantminds.vibrantminds.security;

import com.vibrantminds.vibrantminds.admin.AdminRepository;
import com.vibrantminds.vibrantminds.candidate.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // First check admin table
        var adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            var admin = adminOpt.get();
            return new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    Collections.singletonList(
                            new SimpleGrantedAuthority(admin.getRole())
                    )
            );
        }

        // Then check candidate table
        var candidateOpt = candidateRepository.findByEmail(email);
        if (candidateOpt.isPresent()) {
            var candidate = candidateOpt.get();
            return new User(
                    candidate.getEmail(),
                    candidate.getPassword(),
                    Collections.singletonList(
                            new SimpleGrantedAuthority(candidate.getRole())
                    )
            );
        }

        throw new UsernameNotFoundException(
                "User not found with email : " + email
        );
    }
}