# Spring Batch - Catalogo Musical

Aplicacion de procesamiento por lotes que lee un archivo CSV con canciones, aplica transformaciones y guarda los resultados en una base de datos MySQL.

## Que hace

1. Lee `canciones.csv` con 10 canciones (titulo, artista, genero, duracion, streams)
2. Procesa cada cancion: normaliza el texto a mayusculas, calcula el pago estimado por streams y asigna una clasificacion de popularidad
3. Inserta los resultados en la tabla `canciones_procesadas` en MySQL

## Logica del Processor

- Pago estimado: `streams x $0.004` (tarifa estandar por stream)
- Clasificacion de popularidad:
  - VIRAL: 8,000,000 streams o mas
  - POPULAR: 5,000,000 a 7,999,999
  - MODERADA: 2,000,000 a 4,999,999
  - NICHO: menos de 2,000,000

## Tecnologias

- Java 21
- Spring Boot 3.5.11
- Spring Batch 5.2.4
- MySQL 8.0
- Maven


