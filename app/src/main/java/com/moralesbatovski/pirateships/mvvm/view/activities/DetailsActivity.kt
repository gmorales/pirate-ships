package com.moralesbatovski.pirateships.mvvm.view.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.moralesbatovski.pirateships.R
import com.moralesbatovski.pirateships.commons.PirateShipDependencyHierarchy
import com.moralesbatovski.pirateships.data.local.PirateShip
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

/**
 * @author Gustavo Morales
 *
 * Details Activity class.
 */
class DetailsActivity : AppCompatActivity() {

    companion object {

        private const val PIRATE_SHIP_SELECTED = "pirate_ship_selected"
        private const val TITLE_TRANSITION_NAME = "title_transition"
        private const val PRICE_TRANSITION_NAME = "price_transition"
        private const val IMAGE_TRANSITION_NAME = "image_transition"
        private const val GREETING_AH_KEY = "ah"
        private const val GREETING_AY_KEY = "ay"
        private const val GREETING_AR_KEY = "ar"
        private const val GREETING_YO_KEY = "yo"

        fun start(
                context: Context,
                pirateShip: PirateShip,
                tvTitle: TextView,
                tvPrice: TextView,
                ivPirateShip: ImageView
        ) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(PIRATE_SHIP_SELECTED, pirateShip)
            intent.putExtra(TITLE_TRANSITION_NAME, ViewCompat.getTransitionName(tvTitle))
            intent.putExtra(PRICE_TRANSITION_NAME, ViewCompat.getTransitionName(tvPrice))
            intent.putExtra(IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(ivPirateShip))

            val pairOne = Pair.create(tvTitle as View, ViewCompat.getTransitionName(tvTitle))
            val pairTwo = Pair.create(tvPrice as View, ViewCompat.getTransitionName(tvPrice))
            val pairThree = Pair.create(ivPirateShip as View, ViewCompat.getTransitionName(ivPirateShip))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context as Activity,
                    pairOne,
                    pairTwo,
                    pairThree
            )

            context.startActivity(intent, options.toBundle())
        }

        fun start(context: Context, postId: Int) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(PIRATE_SHIP_SELECTED, postId)
            context.startActivity(intent)
        }
    }

    private var selectedPirateShip: PirateShip? = null

    private val component by lazy { PirateShipDependencyHierarchy.detailsComponent() }

    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()
        setContentView(R.layout.activity_details)
        component.inject(this)
        getIntentData()

        fab_pirate_ship.setOnClickListener {
            showNewNameDialog(selectedPirateShip?.greetingType)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        fab_pirate_ship.visibility = View.GONE
    }

    private fun setUpToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showNewNameDialog(greetingType: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.greeting_type))
        builder.setMessage(
                when (greetingType) {
                    GREETING_AH_KEY -> getString(R.string.greeting_ah)
                    GREETING_AY_KEY -> getString(R.string.greeting_ay)
                    GREETING_AR_KEY -> getString(R.string.greeting_ar)
                    GREETING_YO_KEY -> getString(R.string.greeting_yo)
                    else -> getString(R.string.greeting_ah)
                }
        )
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun getIntentData() {
        if (!intent.hasExtra(PIRATE_SHIP_SELECTED)) {
            finish()
            return
        }

        selectedPirateShip = intent.getParcelableExtra(PIRATE_SHIP_SELECTED)

        tv_title.text = selectedPirateShip?.title
        tv_description.text = selectedPirateShip?.description
        tv_price.text = String.format(getString(R.string.price), selectedPirateShip?.price)

        picasso.load(selectedPirateShip?.image).into(iv_pirate_ship)

        handleTransition(intent.extras)
    }

    private fun handleTransition(extras: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv_title.transitionName = extras?.getString(TITLE_TRANSITION_NAME)
            tv_price.transitionName = extras?.getString(PRICE_TRANSITION_NAME)
            iv_pirate_ship.transitionName = extras?.getString(IMAGE_TRANSITION_NAME)
        }
    }
}
