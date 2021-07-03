package me.kjgleh.springboot.batch.entity

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null
    private var amount: Long
    private var txName: String
    private var txDateTime: LocalDateTime

    constructor(amount: Long, txName: String, txDateTime: String?) {
        this.amount = amount
        this.txName = txName
        this.txDateTime = LocalDateTime.parse(txDateTime, FORMATTER)
    }

    constructor(amount: Long, txName: String, txDateTime: LocalDateTime) {
        this.amount = amount
        this.txName = txName
        this.txDateTime = txDateTime
    }

    constructor(id: Long?, amount: Long, txName: String, txDateTime: String?) {
        this.id = id
        this.amount = amount
        this.txName = txName
        this.txDateTime = LocalDateTime.parse(txDateTime, FORMATTER)
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
    }
}