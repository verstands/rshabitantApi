package vtech.rshabitant.Services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vtech.rshabitant.Exceptions.ResourceAlreadyExistsException;
import vtech.rshabitant.Models.Agent;
import vtech.rshabitant.Repository.AgentRepository;

@AllArgsConstructor
@Service
public class AgentService {
    private final AgentRepository agentRepository;

    public Agent create(Agent agent){
        try {
            return agentRepository.save(agent);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceAlreadyExistsException("An agent with this email already exists");
        }
    }
}   
