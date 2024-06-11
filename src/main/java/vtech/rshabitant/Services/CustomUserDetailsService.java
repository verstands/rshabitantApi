package vtech.rshabitant.Services;

import vtech.rshabitant.Models.Agent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.AllArgsConstructor;
import vtech.rshabitant.Repository.AgentRepository;

@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AgentRepository agentRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Agent agent = agentRepository.findByEmail(email);
        if (agent == null) {
            throw new UsernameNotFoundException("L'utilisateur avec cette email n'existe pas : " + email);
        }
        return agent;
    }

}
