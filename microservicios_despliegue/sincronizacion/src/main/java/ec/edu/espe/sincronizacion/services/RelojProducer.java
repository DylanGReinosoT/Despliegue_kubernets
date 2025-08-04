package ec.edu.espe.sincronizacion.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.sincronizacion.dto.HoraServidorDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RelojProducer {
    @Autowired
    private AmqpTemplate template;

    @Autowired
    private ObjectMapper mapper;

    public void enviarHora(long horaServidor, Map<String, Long> diferencias) {
        try {
            HoraServidorDto dto = new HoraServidorDto(horaServidor, diferencias);
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("reloj.sincronizacion", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
