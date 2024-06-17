package vtech.rshabitant.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import vtech.rshabitant.Models.Agent;
public interface  AgentRepository extends CrudRepository<Agent, Integer>  {
    Optional<Agent> findByEmail(String email);
}
