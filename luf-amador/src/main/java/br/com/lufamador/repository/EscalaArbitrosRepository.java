package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalaArbitrosRepository extends JpaRepository<EscalaArbitros, Long> {
    EscalaArbitros findByNumero(final String numero);
    EscalaArbitros findByCodigo(Long codigo);
}
