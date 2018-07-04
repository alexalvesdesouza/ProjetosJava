package br.com.helpdesk.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.helpdesk.api.dto.Summary;
import br.com.helpdesk.api.entity.ChangeStatus;
import br.com.helpdesk.api.entity.Ticket;
import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.enums.ProfileEnum;
import br.com.helpdesk.api.enums.StatusEnum;
import br.com.helpdesk.api.response.Response;
import br.com.helpdesk.api.security.jwt.JwtTokenUtil;
import br.com.helpdesk.api.service.TicketService;
import br.com.helpdesk.api.service.UserService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "*")
public class TicketController {

  private final UserService   userService;
  private final TicketService ticketService;
  private final JwtTokenUtil  jwtTokenUtil;

  @Autowired
  public TicketController(UserService userService, TicketService ticketService, JwtTokenUtil jwtTokenUtil) {
    this.userService = userService;
    this.ticketService = ticketService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  public ResponseEntity<Response<Ticket>> create(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result) {

    Response<Ticket> response = new Response<Ticket>();

    try {
      this.validateCreateTicket(ticket, result);
      if (result.hasErrors()) {
        result.getAllErrors()
              .forEach(error -> response.getErrors()
                                        .add(error.getDefaultMessage()));
        return ResponseEntity.badRequest()
                             .body(response);
      }
      ticket.setStatus(StatusEnum.getStatusTicket("New"));
      ticket.setUser(this.userFromRequest(request));
      ticket.setDate(new Date());
      ticket.setNumber(this.generateNumber());
      Ticket saved = this.ticketService.createOrUpdate(ticket);
      response.setData(saved);

    } catch (Exception e) {

    }

    return ResponseEntity.ok(response);
  }

  private Integer generateNumber() {
    Random random = new Random();
    return random.nextInt(9999);
  }

  private User userFromRequest(HttpServletRequest request) {
    final String token = request.getHeader("Authorization");
    final String email = jwtTokenUtil.getUserNameFromToken(token);
    return userService.findByEmail(email);
  }

  private void validateCreateTicket(Ticket ticket, BindingResult result) {
    if (null == ticket.getTitle()) {
      result.addError(new ObjectError("Ticket",
                                      "Title no information"));
      return;
    }
  }

  private void validateUpdateTicket(Ticket ticket, BindingResult result) {
    if (null == ticket.getId()) {
      result.addError(new ObjectError("Ticket",
                                      "Id no found"));
      return;
    }
    if (null == ticket.getTitle()) {
      result.addError(new ObjectError("Ticket",
                                      "Title no information"));
      return;
    }
  }

  @PutMapping
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  public ResponseEntity<Response<Ticket>> update(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result) {

    Response<Ticket> response = new Response<Ticket>();

    try {
      this.validateUpdateTicket(ticket, result);
      if (result.hasErrors()) {
        result.getAllErrors()
              .forEach(error -> response.getErrors()
                                        .add(error.getDefaultMessage()));
        return ResponseEntity.badRequest()
                             .body(response);
      }
      Ticket ticketCurrent = this.ticketService.findById(ticket.getId());
      ticket.setStatus(ticketCurrent.getStatus());
      ticket.setUser(ticketCurrent.getUser());
      ticket.setDate(ticketCurrent.getDate());
      ticket.setNumber(ticketCurrent.getNumber());

      if (ticketCurrent.getAssignedUser() != null) {
        ticket.setAssignedUser(ticketCurrent.getAssignedUser());
      }

      Ticket saved = this.ticketService.createOrUpdate(ticket);
      response.setData(saved);
    } catch (Exception e) {
      response.getErrors()
              .add(e.getMessage());
      return ResponseEntity.badRequest()
                           .body(response);
    }

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "{id}")
  @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
  public ResponseEntity<Response<Ticket>> findById(@PathVariable("id") String id) {
    Response<Ticket> response = new Response<Ticket>();
    Ticket ticket = this.ticketService.findById(id);

    if (null == ticket) {
      response.getErrors()
              .add("Register not found: " + id);
      return ResponseEntity.badRequest()
                           .body(response);
    }

    List<ChangeStatus> changes = new ArrayList<ChangeStatus>();
    Iterable<ChangeStatus> changesCurrent = this.ticketService.listChangeStatus(ticket.getId());
    for (Iterator<ChangeStatus> iterator = changesCurrent.iterator(); iterator.hasNext();) {
      ChangeStatus changeStatus = iterator.next();
      changeStatus.setTicket(null);
      changes.add(changeStatus);
    }
    ticket.setChanges(changes);
    response.setData(ticket);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
    Response<String> response = new Response<String>();
    Ticket ticket = this.ticketService.findById(id);

    if (null == ticket) {
      response.getErrors()
              .add("Register not found: " + id);
      return ResponseEntity.badRequest()
                           .body(response);
    }
    this.ticketService.delete(id);
    return ResponseEntity.ok(new Response<String>());
  }

  @GetMapping(value = "{page}/{count}")
  @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
  public ResponseEntity<Response<Page<Ticket>>> findAll(HttpServletRequest request, @PathVariable("page") int page,
                                                        @PathVariable("count") int count) {

    Response<Page<Ticket>> response = new Response<Page<Ticket>>();
    Page<Ticket> tickets = null;
    User userRequest = userFromRequest(request);

    if (userRequest.getProfile()
                   .equals(ProfileEnum.ROLE_TECHNICIAN)) {
      tickets = this.ticketService.listTicket(page, count);
    } else if (userRequest.getProfile()
                          .equals(ProfileEnum.ROLE_CUSTOMER)) {
      tickets = this.ticketService.findByCurrentUser(page, count, userRequest.getId());
    }

    response.setData(tickets);
    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "{page}/{count}/{number}/{title}/{status}/{priority}/{assigned}")
  @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
  public ResponseEntity<Response<Page<Ticket>>> findByParameters(HttpServletRequest request, 
                                                                 @PathVariable("page") int page,
                                                                 @PathVariable("count") int count, 
                                                                 @PathVariable("number") Integer number,
                                                                 @PathVariable("title") String title, @PathVariable("status") String status,
                                                                 @PathVariable("priority") String priority,
                                                                 @PathVariable("assigned") boolean assigned) {

    title = title.equals("uninformed") ? "" : title;
    status = status.equals("uninformed") ? "" : status;
    priority = priority.equals("uninformed") ? "" : priority;

    Response<Page<Ticket>> response = new Response<Page<Ticket>>();
    Page<Ticket> tickets = null;

    if (number > 0) {
      tickets = this.ticketService.findByNumber(page, count, number);
    } else {
      User userRequest = userFromRequest(request);
      if (userRequest.getProfile()
                     .equals(ProfileEnum.ROLE_TECHNICIAN)) {
        if (assigned) {
          tickets = this.ticketService.findByParametersAndAssignedUser(page, count, title, status, priority, userRequest.getId());
        } else {
          tickets = this.ticketService.findByParameters(page, count, title, status, priority);
        }
      } else if (userRequest.getProfile()
                            .equals(ProfileEnum.ROLE_CUSTOMER)) {
        tickets = this.ticketService.findByParametersAndCurrentUser(page, count, title, status, priority, userRequest.getId());
      }
    }

    response.setData(tickets);
    return ResponseEntity.ok(response);

  }

  @PutMapping(value = "{id}/{status}")
  @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
  public ResponseEntity<Response<Ticket>> changeStatus(@PathVariable("id") String id, @PathVariable("status") String status,
                                                       HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result) {

    Response<Ticket> response = new Response<Ticket>();
    User user = userFromRequest(request);
    try {
      this.validateChangeStatus(id, status, result);
      if (result.hasErrors()) {
        result.getAllErrors()
              .forEach(error -> response.getErrors()
                                        .add(error.getDefaultMessage()));
        return ResponseEntity.badRequest()
                             .body(response);
      }
      Ticket ticketCurrent = this.ticketService.findById(id);
      ticketCurrent.setStatus(StatusEnum.getStatusTicket(status));
      if (status.equals("Assigned")) {
        ticketCurrent.setAssignedUser(user);
      }
      Ticket persisted = this.ticketService.createOrUpdate(ticketCurrent);
      ChangeStatus changeStatus = new ChangeStatus();
      changeStatus.setUserChange(user);
      changeStatus.setDateChangeStatus(new Date());
      changeStatus.setStatus(StatusEnum.getStatusTicket(status));
      changeStatus.setTicket(persisted);
      this.ticketService.createChangeStatus(changeStatus);
      response.setData(persisted);
    } catch (Exception e) {
      response.getErrors()
              .add(e.getMessage());
      return ResponseEntity.badRequest()
                           .body(response);
    }


    return ResponseEntity.ok(response);
  }

  private void validateChangeStatus(String id, String status, BindingResult result) {
    if (null == id || "".equals(id)) {
      result.addError(new ObjectError("Ticket",
                                      "Id no information"));
    }
    if (null == status || "".equals(status)) {
      result.addError(new ObjectError("Ticket",
                                      "Status no information"));
    }

  }

  @GetMapping(value = "/summary")
  public ResponseEntity<Response<Summary>> findSummary() {
    Response<Summary> response = new Response<Summary>();

    int amountNew = 0;
    int amountResolved = 0;
    int amountApproved = 0;
    int amountDisapprovad = 0;
    int amountAssigned = 0;
    int amountClosed = 0;

    Iterable<Ticket> tickets = this.ticketService.findAll();
    if (null != tickets) {
      for (Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext();) {
        Ticket ticket = iterator.next();

        if (StatusEnum.New.equals(ticket.getStatus()))
          amountNew++;
        if (StatusEnum.Resolved.equals(ticket.getStatus()))
          amountResolved++;
        if (StatusEnum.Approved.equals(ticket.getStatus()))
          amountApproved++;
        if (StatusEnum.Disapproved.equals(ticket.getStatus()))
          amountDisapprovad++;
        if (StatusEnum.Assigned.equals(ticket.getStatus()))
          amountAssigned++;
        if (StatusEnum.Closed.equals(ticket.getStatus()))
          amountClosed++;
      }
    }

    Summary summary = new Summary();
    summary.setAmountNew(amountNew);
    summary.setAmountResolved(amountResolved);
    summary.setAmountApproved(amountApproved);
    summary.setAmountDisapprovad(amountDisapprovad);
    summary.setAmountAssigned(amountAssigned);
    summary.setAmountClosed(amountClosed);

    response.setData(summary);
    return ResponseEntity.ok(response);
  }

}
