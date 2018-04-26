package com.moralesbatovski.pirateships.mvvm.view.adapters

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.moralesbatovski.pirateships.R
import com.moralesbatovski.pirateships.data.local.PirateShip
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pirate_ship_item.view.*

/**
 * @author Gustavo Morales
 *
 * List Adapter class.
 */
class ListAdapter(private val picasso: Picasso) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var data = emptyList<PirateShip>()
    var interactor: PostInteractor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.pirate_ship_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder?.bind(data[position], picasso)

        if (holder?.itemView != null)
            holder.itemView.setOnClickListener { view ->
                with(view) {
                    interactor?.postClicked(data[position], tv_title, tv_price, iv_pirate_ship)
                }
            }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<PirateShip>?) {
        this.data = data ?: this.data
        notifyDataSetChanged()
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pirateShip: PirateShip, picasso: Picasso) {

            val formattedTitle = pirateShip.title ?: ""
            val formattedPrice = String.format(itemView.resources.getString(R.string.price), pirateShip.price)

            with(pirateShip) {
                itemView.tv_title.text = formattedTitle
                itemView.tv_price.text = formattedPrice
                picasso.load(image).into(itemView.iv_pirate_ship)

                //SharedItem transition
                ViewCompat.setTransitionName(itemView.tv_title, formattedTitle)
                ViewCompat.setTransitionName(itemView.tv_price, formattedPrice)
                ViewCompat.setTransitionName(itemView.iv_pirate_ship, image)
            }
        }
    }

    interface PostInteractor {
        fun postClicked(pirateShip: PirateShip, tvTitle: TextView, tvPrice: TextView, ivPirateShip: ImageView)
    }
}