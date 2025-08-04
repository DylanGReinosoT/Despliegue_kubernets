package publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.NotificacionDto;
import publicaciones.dto.CatalogoDto;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarACatalogo(String mensaje, String tipo, Object datos) {
        try {
            CatalogoDto dto = new CatalogoDto(mensaje, tipo, datos);
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("catalogo.cola", json);
            System.out.println("Mensaje enviado al catálogo: " + json);
        } catch (Exception e) {
            System.err.println("Error enviando mensaje al catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}