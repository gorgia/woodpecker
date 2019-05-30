package webint.woodpecker.acquisition.scheduler

import org.hibernate.service.spi.Stoppable
import java.time.LocalDateTime

import kotlin.properties.Delegates


abstract class Task: Stoppable, Runnable {

    abstract var startDateTime: LocalDateTime
    abstract var endDateTime: LocalDateTime

    var isRunning: Boolean by Delegates.observable(false)    {
        _, old, new -> if(new!=old) {if(new) this.run() else this.stop()}
    }


    fun checkDates(): Boolean{
        return startDateTime.isBefore(LocalDateTime.now()) && endDateTime.isAfter(LocalDateTime.now())
    }

}