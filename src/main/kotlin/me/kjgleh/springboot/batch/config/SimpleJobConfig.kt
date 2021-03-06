package me.kjgleh.springboot.batch.config

import mu.KLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용한다.
@Configuration
class SimpleJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    companion object : KLogging()

    @Bean
    fun simpleJob(): Job {
        return jobBuilderFactory.get("simpleJob") // simpleJob 이란 이름의 Batch Job을 생성한다.
            .start(simpleStep1())
            .build()
    }

    @Bean
    fun simpleStep1(): Step {
        if (true) {
            logger.debug { "true" }
        }
        return stepBuilderFactory.get("simpleStep1") // simpleStep1 이란 이름의 Batch Step을 생성한다.
            .tasklet { contribution, chunkContext -> // Step 안에서 수행될 기능들을 명시한다.
                logger.info(">>> Step1")
                RepeatStatus.FINISHED
            }
            .build()
    }
}
