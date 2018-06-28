package br.com.helpdesk.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.mongodb.DuplicateKeyException;

import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.response.Response;
import br.com.helpdesk.api.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

  private final UserService     userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Response<User>> create(HttpServletRequest request, @RequestBody User user, BindingResult result) {

    Response<User> response = new Response<User>();

    try {
      this.validateCreateUser(user, result);
      if (result.hasErrors()) {
        result.getAllErrors()
              .forEach(error -> response.getErrors()
                                        .add(error.getDefaultMessage()));
        return ResponseEntity.badRequest()
                             .body(response);
      }

      user.setPassword(passwordEncoder.encode(user.getPassword()));
      User saved = userService.createOrUpdate(user);
      response.setData(saved);

    } catch (DuplicateKeyException dE) {
      response.getErrors()
              .add("E-mail already registered!");
      return ResponseEntity.badRequest()
                           .body(response);
    } catch (Exception e) {
      response.getErrors()
              .add(e.getMessage());
      return ResponseEntity.badRequest()
                           .body(response);
    }

    return ResponseEntity.ok(response);
  }

  private void validateCreateUser(User user, BindingResult result) {
    if (null == user.getEmail())
      result.addError(new ObjectError("User",
                                      "Email não informado"));
  }

  @PutMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Response<User>> update(HttpServletRequest request, @RequestBody User user, BindingResult result) {

    Response<User> response = new Response<User>();

    try {

      this.validateUpdataUser(user, result);
      if (result.hasErrors()) {
        result.getAllErrors()
              .forEach(error -> response.getErrors()
                                        .add(error.getDefaultMessage()));
        return ResponseEntity.badRequest()
                             .body(response);
      }

      user.setPassword(passwordEncoder.encode(user.getPassword()));
      User saved = userService.createOrUpdate(user);
      response.setData(saved);

    } catch (Exception e) {
      response.getErrors()
              .add(e.getMessage());
      return ResponseEntity.badRequest()
                           .body(response);
    }

    return ResponseEntity.ok(response);
  }

  private void validateUpdataUser(User user, BindingResult result) {
    if (null == user.getId())
      result.addError(new ObjectError("User",
                                      "Id not information"));

    if (null == user.getEmail())
      result.addError(new ObjectError("User",
                                      "Email not information"));
  }

  @GetMapping(value = "{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Response<User>> findById(@PathVariable("id") String id) {
    Response<User> response = new Response<User>();
    User user = this.userService.findById(id);
    if (null == user) {
      response.getErrors()
              .add("Register not found " + id);
      return ResponseEntity.badRequest()
                           .body(response);
    }
    response.setData(user);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(value = "{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Response<String>> deleteById(@PathVariable("id") String id) {
    Response<String> response = new Response<String>();
    User user = this.userService.findById(id);

    if (null == user) {
      response.getErrors()
              .add("Register not found " + id);
      return ResponseEntity.badRequest()
                           .body(response);
    }
    this.userService.delete(id);
    return ResponseEntity.ok(new Response<String>());
  }

  @GetMapping(value = "{page}/{count}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Response<Page<User>>> findAll(@PathVariable("page") int page, @PathVariable("count") int count) {
    Response<Page<User>> response = new Response<Page<User>>();
    Page<User> users = this.userService.findAll(page, count);
    response.setData(users);
    return ResponseEntity.ok(response);
  }

}
