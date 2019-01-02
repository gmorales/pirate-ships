package com.moralesbatovski.pirateships.mvvm.view.activities

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moralesbatovski.pirateships.R
import com.moralesbatovski.pirateships.commons.PirateShipDependencyHierarchy
import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.mvvm.view.adapters.ListAdapter
import com.moralesbatovski.pirateships.mvvm.viewmodel.ListViewModel
import com.moralesbatovski.pirateships.mvvm.viewmodel.ListViewModelFactory
import com.moralesbatovski.pirateships.networking.Outcome
import kotlinx.android.synthetic.main.activity_list.*
import java.io.IOException
import javax.inject.Inject

/**
 * @author Gustavo Morales
 *
 * List Activity class.
 */
class ListActivity : AppCompatActivity(), ListAdapter.PirateShipInteractor {

    private val component by lazy { PirateShipDependencyHierarchy.listComponent() }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var adapter: ListAdapter

    private val viewModel: ListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
    }

    private val context: Context by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        component.inject(this)

        adapter.interactor = this
        rvPirateShips.adapter = adapter
        srlPrivateShips.setOnRefreshListener {
            viewModel.refreshPirateShips()
        }

        viewModel.getPirateShips()
        initiateDataListener()
    }

    /**
     * This method observes the outcome and also update state of the screen accordingly.
     */
    private fun initiateDataListener() {
        viewModel.pirateShipsOutcome.observe(this, Observer<Outcome<List<PirateShip>>> { outcome ->
            when (outcome) {

                is Outcome.Progress -> srlPrivateShips.isRefreshing = outcome.loading

                is Outcome.Success -> adapter.setData(outcome.data)

                is Outcome.Failure -> {
                    if (outcome.e is IOException) {
                        Toast.makeText(context, R.string.need_internet, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, R.string.failed_load_try_again, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    override fun pirateShipClicked(pirateShip: PirateShip, tvTitle: TextView, tvPrice: TextView, ivPirateShip: ImageView) {
        DetailsActivity.start(context, pirateShip, tvTitle, tvPrice, ivPirateShip)
    }
}
