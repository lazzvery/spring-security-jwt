package chat.jamsi.domain.common.security;

import chat.jamsi.domain.member.domain.Member;
import chat.jamsi.domain.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member =
                authRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid authentication!"));

        return CustomUserDetails.of(member);
    }

}
