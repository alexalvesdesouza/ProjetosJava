package com.ideaapi.repository.agendamento;

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

import com.ideaapi.model.Agendamento;
import com.ideaapi.model.Agendamento_;
import com.ideaapi.model.Funcionario_;
import com.ideaapi.model.Horario_;
import com.ideaapi.repository.filter.AgendamentoFilter;
import com.ideaapi.repository.projection.ResumoAgendamento;

public class AgendamentoRepositoryImpl implements AgendamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Agendamento> filtrar(AgendamentoFilter agendamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Agendamento> criteria = builder.createQuery(Agendamento.class);
        Root<Agendamento> root = criteria.from(Agendamento.class);

        Predicate[] predicates = criarRestricoes(agendamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Agendamento> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(agendamentoFilter));
    }


    @Override
    public Page<ResumoAgendamento> resumir(AgendamentoFilter agendamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ResumoAgendamento> criteria = builder.createQuery(ResumoAgendamento.class);
        Root<Agendamento> root = criteria.from(Agendamento.class);

        criteria.select(builder.construct(ResumoAgendamento.class
                , root.get(Agendamento_.codigo)
                , root.get(Agendamento_.observacao)
                , root.get(Agendamento_.horario).get(Horario_.dataExame)
                , root.get(Agendamento_.tipo)
                , root.get(Agendamento_.funcionario).get(Funcionario_.nome)));

        Predicate[] predicates = criarRestricoes(agendamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<ResumoAgendamento> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(agendamentoFilter));
    }

    private Predicate[] criarRestricoes(AgendamentoFilter agendamentoFilter, CriteriaBuilder builder,
            Root<Agendamento> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(agendamentoFilter.getObservacao())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Agendamento_.observacao)),
                    "%" + agendamentoFilter.getObservacao().toLowerCase() + "%"));
        }

        if (agendamentoFilter.getDataExameDe() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get(Agendamento_.horario).get(Horario_.dataExame),
                            agendamentoFilter.getDataExameDe()));
        }

        if (agendamentoFilter.getDataExameAte() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get(Agendamento_.horario).get(Horario_.dataExame),
                            agendamentoFilter.getDataExameAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(AgendamentoFilter agendamentoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Agendamento> root = criteria.from(Agendamento.class);

        Predicate[] predicates = criarRestricoes(agendamentoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

}
