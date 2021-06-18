package me.kjgleh.springboot.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing // 배치 기능 활성화
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
