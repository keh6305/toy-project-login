package com.toy.login.service.login;

import com.toy.login.model.dto.MemberDto;
import com.toy.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Object signup(MemberDto.SignupRequest signupRequest) {
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        signupRequest.setRole("ROLE_USER");

        try {
            int result = loginRepository.signup(signupRequest);

            System.out.println(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}