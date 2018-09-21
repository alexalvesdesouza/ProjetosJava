package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Video;
import br.com.lufamador.service.impl.VideoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoServiceImpl videoService;

    @GetMapping
    public ResponseEntity<List<Video>> geVideos() {
        List<Video> list = this.videoService.findAll();
        return ResponseEntity.ok(list);
    }

}
