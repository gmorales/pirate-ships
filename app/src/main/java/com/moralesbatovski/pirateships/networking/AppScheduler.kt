package com.moralesbatovski.pirateships.networking

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Gustavo Morales
 *
 * Implementation of [Scheduler] with actual threads.
 */
class AppScheduler : Scheduler {

    override fun mainThread(): io.reactivex.Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): io.reactivex.Scheduler {
        return Schedulers.io()
    }
}