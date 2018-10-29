package br.com.lufamador.jobs;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.lufamador.service.impl.ClassificacaoService;

@Component
@EnableScheduling
public class ClassificacaoJob {

    private final Logger logger = LoggerFactory.getLogger(ClassificacaoJob.class);
    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;

    @Autowired
    private ClassificacaoService classificacaoService;

    @Scheduled(fixedDelay = HORA)
    protected void loadClassificacaoDuplicadas() {
        logger.info("Load classificação duplicada iniciado em: " + LocalDateTime.now());
        this.classificacaoService.loadClassificacoesDuplicadas();
    }
}
