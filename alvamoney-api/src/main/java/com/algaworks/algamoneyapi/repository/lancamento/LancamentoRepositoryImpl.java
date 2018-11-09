package com.algaworks.algamoneyapi.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = criteriaBuilder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = this.criaRestricoes(filter, criteriaBuilder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = this.em.createQuery(criteria);
        this.adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private Long total(LancamentoFilter filter) {

        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = this.criaRestricoes(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return this.em.createQuery(criteria).getSingleResult();
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);

    }

    private Predicate[] criaRestricoes(LancamentoFilter filter, CriteriaBuilder criteriaBuilder,
            Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(filter.getDescricao())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")),
                    "%" + filter.getDescricao().toLowerCase() + "%"));
        }

        if (!StringUtils.isEmpty(filter.getDataVencimentoDe())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataVencimento"),
                    filter.getDataVencimentoDe()));
        }
        if (!StringUtils.isEmpty(filter.getDataVencimentoAte())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataVencimento"),
                    filter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
