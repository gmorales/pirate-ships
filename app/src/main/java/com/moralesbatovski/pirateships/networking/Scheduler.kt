package com.moralesbatovski.pirateships.networking

import io.reactivex.Scheduler

/**
 * @author Gustavo Morales
 *
 * Scheduler Interface.
 */
interface Scheduler {

    fun mainThread(): Scheduler

    fun io(): Scheduler
}