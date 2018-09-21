package br.com.lufamador.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.galeria.Video;
import br.com.lufamador.repository.VideoRepository;
import br.com.lufamador.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository repository;
    private final String GET_YOUTUBE = "https://www.youtube.com/embed/";

    @Override
    public Page<Video> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count);
        return this.repository.findAll(pages);
    }

    @Override
    public Video createOrUpdate(Video entity) {
        getSrc(entity);
        return this.repository.saveAndFlush(entity);
    }

    private void getSrc(Video entity) {
       String link = entity.getImage();
       String src = link.replace(GET_YOUTUBE, "");
       entity.setSrc(src);
    }

    @Override
    public Video findByCodigo(Long codigo) {
        return this.repository.findById(codigo).get();
    }

    @Override
    public void delete(Long codigo) {
        this.repository.delete(this.findByCodigo(codigo));
    }
}
