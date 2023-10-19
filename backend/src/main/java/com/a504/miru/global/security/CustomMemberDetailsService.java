package com.a504.miru.global.security;

import com.a504.miru.domain.member.Member;
import com.a504.miru.domain.member.MemberService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.NoSuchElementException;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        try {
            Member member = memberService.findByUid(uid);
            return new CustomMemberDetails(member);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new CustomMemberDetails();
        }
    }
}
