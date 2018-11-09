package com.algaworks.algamoneyapi.resource;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.exceptionhandler.AlgaMoneyExceptionHandler;
import com.algaworks.algamoneyapi.exceptions.PessoaInexistenteOuInativaException;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import com.algaworks.algamoneyapi.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.algaworks.algamoneyapi.constants.ErrorsCode.PESSOA_INEXISTENTE_OU_INATIVA;
import static com.algaworks.algamoneyapi.constants.ErrorsCode.RECURSO_NAO_ENCONTRADO;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable pageable) {
        return this.lancamentoService.listaTodasLancamentos(filter, pageable);
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response) {

        final Lancamento lancamentoSalva = this.lancamentoService.cadastraLancamento(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> busca(@PathVariable Long codigo) {
        Lancamento lancamento = this.lancamentoService.buscaLancamento(codigo);

        if (null == lancamento)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(lancamento);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleta(@PathVariable Long codigo) {
        this.lancamentoService.deletaLancamento(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Lancamento> atualiza(@PathVariable Long codigo, @RequestBody @Valid Lancamento lancamento) {
        return this.lancamentoService.atulizaLancamento(codigo, lancamento);

    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexitenteOuInativaException(PessoaInexistenteOuInativaException ex) {

        String mensagemUsuario = this.messageSource.getMessage(PESSOA_INEXISTENTE_OU_INATIVA, null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();

        List<AlgaMoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgaMoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

}
