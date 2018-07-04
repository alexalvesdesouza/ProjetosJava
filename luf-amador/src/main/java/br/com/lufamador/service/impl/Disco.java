package br.com.lufamador.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Disco {

    @Value("${contato.disco.raiz}")
    private String raiz;

    @Value("${contato.disco.diretorio-fotos}")
    private String diretorio;

    public void salvarFoto(MultipartFile file) {
        this.salva(this.diretorio, file);
    }

    public void salva(final String diretorio, MultipartFile file) {
        Path diretorioPath = Paths.get(this.raiz, diretorio);
        Path arquivoPath = diretorioPath.resolve(file.getOriginalFilename());

        try {

            Files.createDirectories(diretorioPath);
            file.transferTo(arquivoPath.toFile());

        } catch (IOException io) {
            throw new RuntimeException("Problemas ao salvar o arquivo");
        }
    }

}
