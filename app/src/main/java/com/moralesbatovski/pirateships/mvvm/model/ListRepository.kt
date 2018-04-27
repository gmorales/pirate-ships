package com.moralesbatovski.pirateships.mvvm.model

import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.networking.Outcome
import com.moralesbatovski.pirateships.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * @author Gustavo Morales
 *
 * List Repository class.
 */
class ListRepository(private val local: ListDataContract.Local,
                     private val remote: ListDataContract.Remote,
                     private val scheduler: Scheduler,
                     private val compositeDisposable: CompositeDisposable)
    : ListDataContract.Repository {

    override val pirateShipFetchOutcome: PublishSubject<Outcome<List<PirateShip>>> = PublishSubject.create<Outcome<List<PirateShip>>>()

    var remoteFetch = true

    override fun fetchPirateShips() {
        pirateShipFetchOutcome.onNext(Outcome.loading(true))
        compositeDisposable.add(local.getPirateShips()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.mainThread())
                .subscribe({ pirateShips ->
                    with(pirateShipFetchOutcome) {
                        onNext(Outcome.loading(false))
                        onNext(Outcome.success(pirateShips))
                    }
                    if (remoteFetch) {
                        refreshPirateShips()
                    }
                    remoteFetch = false
                }, { error -> handleError(error) }))
    }

    override fun refreshPirateShips() {
        pirateShipFetchOutcome.onNext(Outcome.loading(true))
        compositeDisposable.add(remote.getPirateShips()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.mainThread())
                .subscribe(
                        { response -> savePirateShips(response.ships) },
                        { error -> handleError(error) }))
    }

    override fun savePirateShips(pirateShips: List<PirateShip>) {
        local.savePirateShips(pirateShips.filterNotNull())
    }

    override fun handleError(error: Throwable) {
        with(pirateShipFetchOutcome) {
            onNext(Outcome.loading(false))
            onNext(Outcome.failure(error))
        }
    }
}
