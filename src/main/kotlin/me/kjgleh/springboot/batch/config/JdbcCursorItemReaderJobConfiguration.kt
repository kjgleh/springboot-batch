package me.kjgleh.springboot.batch.config

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import me.kjgleh.springboot.batch.entity.Pay
import me.kjgleh.springboot.batch.config.JdbcCursorItemReaderJobConfiguration
import mu.KLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import javax.sql.DataSource

@Configuration
class JdbcCursorItemReaderJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory, // DataSource DI
    private val dataSource: DataSource
) {

    companion object: KLogging() {
        private const val chunkSize = 10
    }

    @Bean
    fun jdbcCursorItemReaderJob(): Job {
        return jobBuilderFactory["jdbcCursorItemReaderJob"]
            .start(jdbcCursorItemReaderStep())
            .build()
    }

    @Bean
    fun jdbcCursorItemReaderStep(): Step {
        return stepBuilderFactory["jdbcCursorItemReaderStep"]
            .chunk<Pay, Pay>(chunkSize)
            .reader(jdbcCursorItemReader())
            .writer(jdbcCursorItemWriter())
            .build()
    }

    @Bean
    fun jdbcCursorItemReader(): JdbcCursorItemReader<Pay> {
        return JdbcCursorItemReaderBuilder<Pay>()
            .fetchSize(chunkSize)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper(Pay::class.java))
            .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
            .name("jdbcCursorItemReader")
            .build()
    }

    /**
     * reader에서 넘어온 데이터를 하나씩 출력하는 writer
     */
    private fun jdbcCursorItemWriter(): ItemWriter<Pay> {
        return ItemWriter { list: List<Pay> ->
            for (pay in list) {
                logger.info("Current Pay={}", pay);
            }
        }
    }
}