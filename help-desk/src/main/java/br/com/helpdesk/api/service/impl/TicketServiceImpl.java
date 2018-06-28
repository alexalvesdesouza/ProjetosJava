package br.com.helpdesk.api.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.helpdesk.api.entity.ChangeStatus;
import br.com.helpdesk.api.entity.Ticket;
import br.com.helpdesk.api.repository.ChangeStatusRepository;
import br.com.helpdesk.api.repository.TicketRepository;
import br.com.helpdesk.api.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

  private final TicketRepository       ticketRepository;
  private final ChangeStatusRepository changeRepository;

  public TicketServiceImpl(TicketRepository ticketRepository, ChangeStatusRepository changeRepository) {
    this.ticketRepository = ticketRepository;
    this.changeRepository = changeRepository;
  }

  @Override
  public Ticket createOrUpdate(Ticket ticket) {
    return ticketRepository.save(ticket);
  }

  @Override
  public Ticket findById(String id) {
    Optional<Ticket> findById = this.ticketRepository.findById(id);
    return findById.orElse(null);
  }

  @Override
  public void delete(String id) {
    ticketRepository.delete(this.findById(id));
  }

  @Override
  public Page<Ticket> listTicket(int page, int size) {
    Pageable pages = PageRequest.of(page, size);
    return this.ticketRepository.findAll(pages);
  }

  @Override
  public ChangeStatus createChangeStatus(ChangeStatus changeStatus) {
    return this.changeRepository.save(changeStatus);
  }

  @Override
  public Iterable<ChangeStatus> listChangeStatus(String ticketId) {
    return this.changeRepository.findByTicketIdOrderByDateChangeStatusDesc(ticketId);
  }

  @Override
  public Page<Ticket> findByCurrentUser(int page, int count, String userId) {
    Pageable pages = PageRequest.of(page, count);
    return this.ticketRepository.findByUserIdOrderByDateDesc(pages, userId);
  }

  @Override
  public Page<Ticket> findByParameters(int page, int count, String title, String status, String priority) {
    Pageable pages = PageRequest.of(page, count);
    return this.ticketRepository.findByTitleIgnoreCaseContainingAndStatusAndPriorityOrderByDateDesc(title, status, priority, pages);
  }

  @Override
  public Page<Ticket> findByParametersAndCurrentUser(int page, int count, String title, String status, String priority, String userId) {
    Pageable pages = PageRequest.of(page, count);
    return this.ticketRepository.findByTitleIgnoreCaseContainingAndStatusAndPriorityAndUserIdOrderByDateDesc(title, status, priority, userId, pages);
  }

  @Override
  public Page<Ticket> findByNumber(int page, int count, Integer number) {
    Pageable pages = PageRequest.of(page, count);
    return this.ticketRepository.findByNumber(number, pages);
  }

  @Override
  public Iterable<Ticket> findAll() {
    return this.ticketRepository.findAll();
  }

  @Override
  public Page<Ticket> findByParametersAndAssignedUser(int page, int count, String title, String status, String priority, String assignedUser) {
    Pageable pages = PageRequest.of(page, count);
    return this.ticketRepository.findByTitleIgnoreCaseContainingAndStatusAndPriorityAndAssignedUserIdOrderByDateDesc(title,
                                                                                                                     status,
                                                                                                                     priority,
                                                                                                                     assignedUser,
                                                                                                                     pages);
  }

}
