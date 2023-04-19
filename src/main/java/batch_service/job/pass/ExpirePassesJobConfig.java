package batch_service.job.pass;

import batch_service.repository.pass.PassEntity;
import batch_service.repository.pass.PassStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
public class ExpirePassesJobConfig {
    private final int CHUNK_SIZE = 1;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    public ExpirePassesJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Job expirePassesJob(){
        return this.jobBuilderFactory.get("expirePassesJob")
                .start(expirePassesStep())
                .build();
    }

    @Bean
    public Step expirePassesStep(){
        return this.stepBuilderFactory.get("expirePassesStep")
                .<PassEntity,PassEntity>chunk(CHUNK_SIZE)
                .reader(expirePassesItemReader())
                .processor(expirePassesItemProcessor())
                .writer(expirePassesItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<PassEntity> expirePassesItemReader(){
        return new JpaCursorItemReaderBuilder<PassEntity>()
                .name("expirePassesItemReader")
                .entityManagerFactory(entityManagerFactory)  // 상태(status)진행중, 종료일시(endedAt)가 현재 시점보다 과거일 경우 만료 대상.
                .queryString("select p from passEntity p where p.status = :status and p.endedAt <= :endedAt")
                .parameterValues(Map.of("status", PassStatus.PROGRESSED, "endedAt", LocalDateTime.now()))
                .build();
    }

    @Bean
    public ItemProcessor<PassEntity,PassEntity> expirePassesItemProcessor(){
        return passEntity -> {
            passEntity.setStatus(PassStatus.EXPIRED);
            passEntity.setExpiredAt(LocalDateTime.now());
            return passEntity;
        };
    }
    //JpaItemWriter 는 영속성 관리를 위해 엔티티 매니저를 필수로 설정해줘야함.
    @Bean
    public JpaItemWriter<PassEntity> expirePassesItemWriter(){
        return new JpaItemWriterBuilder<PassEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
