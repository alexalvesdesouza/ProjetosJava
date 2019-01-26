package br.com.lufamador.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Atleta;
import br.com.lufamador.model.AtletaHistory;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.AtletaHistoryServiceImpl;
import br.com.lufamador.service.impl.AtletaServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/atletas")
public class AtletaController {

    @Autowired
    private AtletaServiceImpl atletaService;

    @Autowired
    private AtletaHistoryServiceImpl atletaHistoryService;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<Atleta>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {
        Response<Page<Atleta>> response = new Response<Page<Atleta>>();
        Page<Atleta> atletas = this.atletaService.findAll(page, count);
        response.setData(atletas);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{codigo}")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Atleta>> findById(@PathVariable("codigo") Long codigo) {
        Response<Atleta> response = new Response<>();
        Atleta atleta = this.atletaService.findByCodigo(codigo);
        if (null == atleta) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(atleta);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{codigo}/history")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<List<AtletaHistory>>> findHistoryById(@PathVariable("codigo") Long codigo) {
        Response<List<AtletaHistory>> response = new Response<>();
        List<AtletaHistory> history = this.atletaService.history(codigo);
        if (null == history) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(history);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST)
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Atleta>> cadastraAtleta(@RequestBody Atleta atleta) {
        Response<Atleta> response = new Response<>();
        final Atleta atletaSaved = this.atletaService.createOrUpdate(atleta);
        response.setData(atletaSaved);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Atleta>> atualizaAtleta(@RequestBody Atleta atleta) {
        Response<Atleta> response = new Response<>();
        final Atleta atletaSaved = this.atletaService.createOrUpdate(atleta);
        response.setData(atletaSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{codigo}/baixa-suspensao/")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Atleta> baixaSuspensaoAtleta(@PathVariable("codigo") Long codigo) {
        final Atleta atletaSuspenso = this.atletaService.baixarSuspensaoAtleta(codigo);
        HttpStatus status = (null == atletaSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<Atleta>(atletaSuspenso,
                status);
    }

    @PutMapping(value = "/{codigo}/suspender/")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Atleta> suspendeAtleta(@PathVariable("codigo") Long codigo) {
        final Atleta atletaSuspenso = this.atletaService.suspenderAtleta(codigo);
        HttpStatus status = (null == atletaSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<Atleta>(atletaSuspenso,
                status);
    }

    @DeleteMapping(value = "{codigo}")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<?> deletaAtleta(@PathVariable("codigo") Long codigo) {
        this.atletaService.delete(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

    @DeleteMapping(value = "/history/{codigo}")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<?> deletaAtletaHistory(@PathVariable("codigo") Long codigo) {
        this.atletaHistoryService.deletaHistory(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

    @PostMapping(value = "/history-new")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<AtletaHistory>> atletaHistory(@RequestBody AtletaHistory history) {
        Response<AtletaHistory> response = new Response<>();
        final AtletaHistory atletaSaved = this.atletaHistoryService.cadastra(history);
        response.setData(atletaSaved);
        return ResponseEntity.ok(response);
    }

}
