package br.com.helpdesk.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.helpdesk.api.entity.ChangeStatus;
import br.com.helpdesk.api.entity.Ticket;

public interface ChangeStatusRepository extends MongoRepository<ChangeStatus, String>{

  Iterable<Ticket> findByTicketIdOrderByDateChangeStatusDesc(Integer ticketId);  
  
  Iterable<ChangeStatus> findByTicketIdOrderByDateChangeStatusDesc(String ticketId);
}
