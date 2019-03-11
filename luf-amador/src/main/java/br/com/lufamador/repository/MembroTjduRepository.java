package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroTjduRepository extends JpaRepository<MembroTjdu, Long> {

    MembroTjdu findByCodigo(Long codigo);
}
