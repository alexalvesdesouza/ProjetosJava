package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Video;
import br.com.lufamador.repository.VideoRepository;

@Service
public class VideoServiceImpl {

    @Autowired
    private VideoRepository repository;

    public List<Video> findAll() {
        return this.repository.findAll();
    }


}
