package br.com.lufamador.jobs;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//@EnableScheduling
public class ClassificacaoJob {

    private final Logger logger = LoggerFactory.getLogger(ClassificacaoJob.class);
    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;

//    @Scheduled(fixedDelay = MINUTO)
    protected void geraClassificacao() {
        System.out.println("Hora-->" + LocalDateTime.now());
    }
}
