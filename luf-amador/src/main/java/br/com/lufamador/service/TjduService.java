package br.com.lufamador.service;

import org.springframework.data.domain.Page;

public interface TjduService {

    Page<Tjdu> findAll(int page, int count);

    Tjdu createOrUpdate(Tjdu entity);

    Tjdu findByCodigo(Long codigo);

    void delete(Long codigo);

    Page<MembroTjdu> findAllMembros(int page, int count);

    MembroTjdu createOrUpdate(MembroTjdu entity);

    MembroTjdu findMembroByCodigo(Long codigo);

    void deleteMenbroTjdu(Long codigo);

}
