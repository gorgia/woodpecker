package webint.woodpecker.acquisition.twitter.streamproducer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

interface Stoppable: Runnable {
    var job: Job
    override fun run() {
        this.job = GlobalScope.launch {  }
    }


    fun stop(){
        if (job.isActive) {
            job.cancel()
        }
    }
}