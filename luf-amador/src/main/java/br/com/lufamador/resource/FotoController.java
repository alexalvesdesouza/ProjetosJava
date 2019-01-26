package br.com.lufamador.resource;

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

import br.com.lufamador.model.galeria.Foto;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.FotoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/fotos")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<Foto>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {
        Response<Page<Foto>> response = new Response<Page<Foto>>();
        Page<Foto> fotos = this.fotoService.findAll(page, count);
        response.setData(fotos);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Foto>> findById(@PathVariable("codigo") Long codigo) {
        Response<Foto> response = new Response<>();
        Foto foto = this.fotoService.findByCodigo(codigo);
        if (null == foto) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(foto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Foto>> cadastraFoto(@RequestBody Foto foto) {
        Response<Foto> response = new Response<>();
        final Foto fotoSaved = this.fotoService.createOrUpdate(foto);
        response.setData(fotoSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Foto>> atualizaFoto(@RequestBody Foto foto) {
        Response<Foto> response = new Response<>();
        final Foto fotoSaved = this.fotoService.createOrUpdate(foto);
        response.setData(fotoSaved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<?> deletaFoto(@PathVariable("codigo") Long codigo) {
        this.fotoService.delete(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

}
