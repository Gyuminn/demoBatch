package org.demo.scheduler.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Job1Config {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job testJob1() {
        return new JobBuilder("testJob", jobRepository)
                .start(testStep1())
                .preventRestart()
                .build();

    }

    @Bean
    public Step testStep1() {
        return new StepBuilder("testStep", jobRepository)
                .tasklet(testTasklet1(), transactionManager).build();
    }

    @Bean
    public Tasklet testTasklet1() {
        return (contribution, chunkContext) -> {
            System.out.println("*****");

            return RepeatStatus.FINISHED;
        };
    }

}
