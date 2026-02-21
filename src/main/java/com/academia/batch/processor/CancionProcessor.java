package com.academia.batch.processor;

import com.academia.batch.model.Cancion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CancionProcessor implements ItemProcessor<Cancion, Cancion> {

    private static final Logger log = LoggerFactory.getLogger(CancionProcessor.class);

    // Spotify paga aprox. $0.004 por stream
    private static final double TARIFA_POR_STREAM = 0.004;

    @Override
    public Cancion process(Cancion cancion) {

        // 1. Normalizar texto
        cancion.setTitulo(cancion.getTitulo().toUpperCase());
        cancion.setArtista(cancion.getArtista().toUpperCase());

        // 2. Calcular pago estimado
        double pago = cancion.getStreams() * TARIFA_POR_STREAM;
        cancion.setPago_estimado(pago);

        // 3. Clasificar popularidad por rangos de streams
        String popularidad;
        if (cancion.getStreams() >= 8_000_000) {
            popularidad = "VIRAL";
        } else if (cancion.getStreams() >= 5_000_000) {
            popularidad = "POPULAR";
        } else if (cancion.getStreams() >= 2_000_000) {
            popularidad = "MODERADA";
        } else {
            popularidad = "NICHO";
        }
        cancion.setPopularidad(popularidad);

        log.info("Procesando: {}", cancion);
        return cancion;
    }
}
