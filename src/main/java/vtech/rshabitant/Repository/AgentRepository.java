package vtech.rshabitant.Repository;

import org.springframework.data.repository.CrudRepository;

import vtech.rshabitant.Models.Agent;
public interface  AgentRepository extends CrudRepository<Agent, Integer>  {
    Agent findByEmail(String email);
}
