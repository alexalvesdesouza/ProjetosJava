package com.ideaapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ideaapi.event.RecursoCriadoEvent;
import com.ideaapi.model.Empresa;
import com.ideaapi.service.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

    @Autowired
    public EmpresaService empresaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
//    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_EMPRESA') and #oauth2.hasScope('read')")
    public List<Empresa> listar() {
        return this.empresaService.listaTodasEmpresas();
    }

    @PostMapping
//    @PreAuthorize(value = "hasAuthority('ROLE_CRIAR_EMPRESA') and #oauth2.hasScope('write')")
    public ResponseEntity<Empresa> criar(@RequestBody @Valid Empresa empresa, HttpServletResponse response) {

        final Empresa empresaSalva = this.empresaService.cadastraEmpresa(empresa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, empresaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
    }

    @GetMapping("/{codigo}")
//    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_EMPRESA') and #oauth2.hasScope('read')")
    public ResponseEntity<Empresa> busca(@PathVariable Long codigo) {
        Empresa empresa = this.empresaService.buscaEmpresa(codigo);

        if (null == empresa)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("/{codigo}")
//    @PreAuthorize(value = "hasAuthority('ROLE_DELETAR_EMPRESA') and #oauth2.hasScope('read')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleta(@PathVariable Long codigo) {
        this.empresaService.deletaEmpresa(codigo);
    }
}
