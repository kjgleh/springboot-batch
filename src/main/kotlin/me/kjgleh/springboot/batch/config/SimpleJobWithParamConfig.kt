package me.kjgleh.springboot.batch.config

import mu.KLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleJobWithParamConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    companion object : KLogging()

    @Bean
    fun simpleJobWithParam(): Job {
        return jobBuilderFactory.get("simpleJobWithParam")
            .start(simpleJobWithParamStep1(null))
            .build()
    }

    @Bean
    @JobScope // jobParameters를 사용하기 위해 추가해야 한다.
    fun simpleJobWithParamStep1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory.get("simpleJobWithParamStep1")
            .tasklet { contribution, chunkContext ->
                logger.info(">>> simpleJobWithParamStep1")
                logger.info(">>> $requestDate")
                RepeatStatus.FINISHED
            }
            .build()
    }
}
