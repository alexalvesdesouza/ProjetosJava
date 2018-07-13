package br.com.lufamador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Atleta;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.AtletaServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/atletas")
public class AtletaController {

  private AtletaServiceImpl atletaService;

  @Autowired
  public AtletaController(AtletaServiceImpl atletaService) {
    this.atletaService = atletaService;
  }

  @GetMapping(value = "{page}/{count}")
  @PreAuthorize("hasAnyRole('SECRETARIA')")
  public ResponseEntity<Response<Page<Atleta>>> findAll(@PathVariable("page") int page, @PathVariable("count") int count) {
    Response<Page<Atleta>> response = new Response<Page<Atleta>>();
    Page<Atleta> atletas = this.atletaService.findAll(page, count);
    response.setData(atletas);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('SECRETARIA')")
  public ResponseEntity<Atleta> cadastraAtleta(@RequestBody Atleta atleta) {
    final Atleta atletaSaved = this.atletaService.createOrUpdate(atleta);
    HttpStatus status = (null == atletaSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
    return new ResponseEntity<Atleta>(atletaSaved,
                                      status);
  }

  @PutMapping
  @PreAuthorize("hasAnyRole('SECRETARIA')")
  public ResponseEntity<Atleta> atualizaAtleta(@RequestBody Atleta atleta) {
    final Atleta atletaSuspenso = this.atletaService.createOrUpdate(atleta);
    HttpStatus status = (null == atletaSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
    return new ResponseEntity<Atleta>(atletaSuspenso,
                                      status);
  }

  @PutMapping(value = "/{codigo}/baixa-suspensao/")
  @PreAuthorize("hasAnyRole('SECRETARIA')")
  public ResponseEntity<Atleta> baixaSuspensaoAtleta(@PathVariable("codigo") Long codigo) {
    final Atleta atletaSuspenso = this.atletaService.baixarSuspensaoAtleta(codigo);
    HttpStatus status = (null == atletaSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
    return new ResponseEntity<Atleta>(atletaSuspenso,
                                      status);
  }

  @PutMapping(value = "/{codigo}/suspender/")
  @PreAuthorize("hasAnyRole('SECRETARIA')")
  public ResponseEntity<Atleta> suspendeAtleta(@PathVariable("codigo") Long codigo) {
    final Atleta atletaSuspenso = this.atletaService.suspenderAtleta(codigo);
    HttpStatus status = (null == atletaSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
    return new ResponseEntity<Atleta>(atletaSuspenso,
                                      status);
  }

  @DeleteMapping(value = "/{codigo}/")
  @PreAuthorize("hasAnyRole('SECRETARIA')")
  public ResponseEntity<?> deletaAtleta(@PathVariable("codigo") Long codigo) {
    this.atletaService.delete(codigo);
    HttpStatus status = HttpStatus.OK;
    return new ResponseEntity<>(status);
  }

}
