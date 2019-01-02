package com.moralesbatovski.pirateships.mvvm.model

import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.networking.Outcome
import com.moralesbatovski.pirateships.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * @author Gustavo Morales
 *
 * Detail Repository class.
 */
class DetailsRepository(
        private val local: DetailsDataContract.Local,
        private val scheduler: Scheduler,
        private val compositeDisposable: CompositeDisposable
) : DetailsDataContract.Repository {

    override val pirateShipFetchOutcome: PublishSubject<Outcome<PirateShip>> =
            PublishSubject.create<Outcome<PirateShip>>()

    override fun fetchPirateShipFor(id: Long?) {

        if (id == null) {
            return
        }

        pirateShipFetchOutcome.onNext(Outcome.loading(true))
        compositeDisposable.add(local.getPirateShipForId(id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.mainThread())
                .subscribe({ pirateShipDetail ->
                    with(pirateShipFetchOutcome) {
                        onNext(Outcome.loading(false))
                        onNext(Outcome.success(pirateShipDetail))
                    }
                }, { error -> handleError(error) }))
    }

    override fun handleError(error: Throwable) {
        with(pirateShipFetchOutcome) {
            onNext(Outcome.loading(false))
            onNext(Outcome.failure(error))
        }
    }
}