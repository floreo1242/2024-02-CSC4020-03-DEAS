package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.DTO.request.RegisterRequest;
import io.github.floreo1242.deas.domain.Member;
import io.github.floreo1242.deas.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean register(RegisterRequest request) {
        if (memberRepository.existsById(request.getId())) {
            return false;
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.builder()
                .id(request.getId())
                .password(encodedPassword)
                .name(request.getName())
                .affiliation(request.getAffiliation())
                .contact(request.getContact())
                .type(request.getType())
                .build();
        memberRepository.save(member);
        return true;
    }
}
