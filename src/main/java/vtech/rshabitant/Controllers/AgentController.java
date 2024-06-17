package vtech.rshabitant.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import vtech.rshabitant.Dto.Authentificationdto;
import vtech.rshabitant.Models.Agent;
import vtech.rshabitant.Securite.JwtService;
import vtech.rshabitant.Services.AgentService;

@RestController
@RequestMapping("agent")
public class AgentController {

    @Autowired
    private AgentService agentService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
        String mdpCrypt = this.passwordEncoder.encode(agent.getMdp());
        agent.setMdp(mdpCrypt);
        Agent savedAgent = agentService.create(agent);
        return ResponseEntity.ok(savedAgent);
    }

    @PostMapping(path = "/auth") 
    public Map<String, String> auth(@RequestBody Authentificationdto authentificationdto) {
        Map<String, Boolean> response = new HashMap<>();
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationdto.username(),
                                                       authentificationdto.password()));
        if(authentication.isAuthenticated()){
           return  this.jwtService.generate(authentificationdto.username());
        }else{
            response.put("Erreur", authentication.isAuthenticated());

        }
        response.put("isAuthenticated", authentication.isAuthenticated());
        return null;
    }
}
