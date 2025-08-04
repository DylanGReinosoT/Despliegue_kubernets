package publicaciones.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import publicaciones.dto.HoraClienteDto;
import publicaciones.service.RelojProducer;

@Configuration
@EnableScheduling
public class SchudullingConfig {
    @Autowired
    private RelojProducer relojProducer;


    @Scheduled(fixedRateString = "#{T(java.util.concurrent.TimeUnit).SECONDS.toMillis(${reloj.intervalo:10})}")
    public void reportarHora(){
        try{
            relojProducer.enviarHora();
            System.out.println("Nodo: ms-publicaciones -> enviando la hora ");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
