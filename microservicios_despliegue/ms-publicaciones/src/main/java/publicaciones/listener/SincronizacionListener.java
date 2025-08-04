package publicaciones.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import publicaciones.dto.HoraServidorDto;

@Component
public class SincronizacionListener {
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "reloj.sincronizacion")
    public void recibirSincronizacion(String mensajeJson) {
        try {
            HoraServidorDto dto = objectMapper.readValue(mensajeJson, HoraServidorDto.class);
            System.out.println("Recibiendo sincronizacion sincronización: " + dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
