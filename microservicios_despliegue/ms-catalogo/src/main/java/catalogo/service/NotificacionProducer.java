package catalogo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import catalogo.dto.NotificacionDto;

@Service
public class NotificacionProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper mapper;

    public void enviarNotificacion(String mensaje, String tipo) {
        try{
            NotificacionDto dto = new NotificacionDto(mensaje, tipo);
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("notificaciones.cola", json);
            System.out.println("Notificación enviada: " + json);
        } catch (Exception e) {
            System.err.println("Error enviando notificación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}