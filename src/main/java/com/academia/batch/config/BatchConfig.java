package com.academia.batch.config;

import com.academia.batch.model.Cancion;
import com.academia.batch.processor.CancionProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    // ═══ READER ═══
    @Bean
    public FlatFileItemReader<Cancion> leerCsv() {
        return new FlatFileItemReaderBuilder<Cancion>()
                .name("cancionReader")
                .resource(new ClassPathResource("canciones.csv"))
                .delimited()
                .names("titulo", "artista", "genero", "duracion_seg", "streams")
                .targetType(Cancion.class)
                .linesToSkip(1)
                .build();
    }

    // ═══ PROCESSOR ═══
    @Bean
    public CancionProcessor procesarCancion() {
        return new CancionProcessor();
    }

    // ═══ WRITER ═══
    @Bean
    public JdbcBatchItemWriter<Cancion> escribirEnBd(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Cancion>()
                .sql("INSERT INTO canciones_procesadas " +
                        "(titulo, artista, genero, duracion_seg, streams, pago_estimado, popularidad) " +
                        "VALUES (:titulo, :artista, :genero, :duracion_seg, :streams, :pago_estimado, :popularidad)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    // ═══ STEP ═══
    @Bean
    public Step paso1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      FlatFileItemReader<Cancion> leerCsv,
                      CancionProcessor procesarCancion,
                      JdbcBatchItemWriter<Cancion> escribirEnBd) {
        return new StepBuilder("paso1", jobRepository)
                .<Cancion, Cancion>chunk(3, transactionManager)
                .reader(leerCsv)
                .processor(procesarCancion)
                .writer(escribirEnBd)
                .build();
    }

    // ═══ JOB ═══
    @Bean
    public Job procesarCatalogoJob(JobRepository jobRepository, Step paso1) {
        return new JobBuilder("procesarCatalogoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(paso1)
                .build();
    }
}
