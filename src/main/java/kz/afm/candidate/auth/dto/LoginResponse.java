package kz.afm.candidate.auth.dto;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class LoginResponse {
    public String token;
    public Set<String> roles;
}
