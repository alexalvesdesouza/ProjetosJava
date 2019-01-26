package br.com.lufamador.resource;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.galeria.Video;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.VideoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping(value = "{page}/{count}")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<Video>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {
        Response<Page<Video>> response = new Response<>();
        Page<Video> videos = this.videoService.findAll(page, count);
        response.setData(videos);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{codigo}")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Video>> findById(@PathVariable("codigo") Long codigo) {
        Response<Video> response = new Response<>();
        Video video = this.videoService.findByCodigo(codigo);
        if (null == video) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(video);
        return ResponseEntity.ok(response);
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Video>> cadastraVideo(@RequestBody Video video) {
        Response<Video> response = new Response<>();
        final Video videoSaved = this.videoService.createOrUpdate(video);
        response.setData(videoSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Video>> atualizaVideo(@RequestBody Video video) {
        Response<Video> response = new Response<>();
        final Video videoSaved = this.videoService.createOrUpdate(video);
        response.setData(videoSaved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{codigo}")
//    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<?> deletaVideo(@PathVariable("codigo") Long codigo) {
        this.videoService.delete(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

}
