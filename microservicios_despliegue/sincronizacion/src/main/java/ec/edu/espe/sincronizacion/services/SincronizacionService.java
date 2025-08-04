package ec.edu.espe.sincronizacion.services;

import ec.edu.espe.sincronizacion.dto.HoraClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SincronizacionService {

    @Autowired
    private RelojProducer relojProducer;

    private final Map<String, Long> tiemposClientes = new ConcurrentHashMap<>();

    private static int INTERVALO_SEGUNDOS = 10;

    public void registrarTiempo(HoraClienteDto dto){

        tiemposClientes.put(dto.getNombreNodo(), dto.getHoraEnviada());
    }

    public void sincronizarRelojes(){
        if(tiemposClientes.size() >=2){
            long ahora = Instant.now().toEpochMilli();
            long promedio = (ahora
                    +tiemposClientes.values().stream().mapToLong(Long::longValue).sum())
                    /(tiemposClientes.size()+1);
            enviarAjusteRelojes(promedio);
        }
    }

    public void enviarAjusteRelojes(long horaServidor){
        System.out.println("Ajustando relojes a la hora" + horaServidor);
        Map<String, Long> diferencias = new ConcurrentHashMap<>();
        for(Map.Entry<String, Long> entry : tiemposClientes.entrySet()){
            diferencias.put(entry.getKey(), horaServidor - entry.getValue());
        }
        tiemposClientes.clear();
        relojProducer.enviarHora(horaServidor, diferencias);

    }
}

