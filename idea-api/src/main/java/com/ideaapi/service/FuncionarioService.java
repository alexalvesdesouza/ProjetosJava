package com.ideaapi.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ideaapi.model.Funcionario;
import com.ideaapi.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> listaTodasFuncionarios() {
        return this.funcionarioRepository.findAll();
    }

    public Funcionario cadastraFuncionario(Funcionario entity) {
        return this.funcionarioRepository.save(entity);
    }

    public Funcionario buscaFuncionario(Long codigo) {
        Funcionario funcionario = this.funcionarioRepository.findOne(codigo);

        if (funcionario == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return funcionario;
    }

    public void deletaFuncionario(Long codigo) {
        this.funcionarioRepository.delete(codigo);
    }

    public ResponseEntity<Funcionario> atulizaFuncionario(Long codigo, Funcionario funcionario) {
        Funcionario funcionarioSalva = this.buscaFuncionario(codigo);
        BeanUtils.copyProperties(funcionario, funcionarioSalva, "codigo");

        this.funcionarioRepository.save(funcionarioSalva);
        return ResponseEntity.ok(funcionarioSalva);
    }

    public void ativaInativaFuncionario(Long codigo, Boolean ativa) {
        Funcionario funcionario = this.buscaFuncionario(codigo);
        funcionario.setAtiva(ativa);
        this.funcionarioRepository.save(funcionario);
    }
}
