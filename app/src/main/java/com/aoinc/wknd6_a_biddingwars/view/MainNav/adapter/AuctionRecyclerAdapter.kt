package com.aoinc.wknd6_a_biddingwars.view.MainNav.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aoinc.wknd6_a_biddingwars.R
import com.aoinc.wknd6_a_biddingwars.data.model.AuctionItem
import com.bumptech.glide.Glide

class AuctionRecyclerAdapter(
    private var itemList: MutableList<AuctionItem>,
    var auctionItemClickListener : AuctionItemClickListener)
: RecyclerView.Adapter<AuctionRecyclerAdapter.ItemViewHolder>() {

    interface AuctionItemClickListener {
        fun loadBiddingFragment(auctionItem: AuctionItem)
    }

    fun updateAllItems(updatedList : MutableList<AuctionItem>) {
        itemList = updatedList
        notifyDataSetChanged()
    }

    fun updateSingleItem(updatedItem : AuctionItem, position: Int) {
        itemList[position] = updatedItem
        notifyItemChanged(position)
    }

    fun getItemList(): MutableList<AuctionItem> {
        return itemList
    }

    fun insertSingleItem(addedItem : AuctionItem, position: Int = 0) {
        itemList.add(position, addedItem)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.auction_item_layout, parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]

        holder.apply {
            Glide.with(itemView)
                .load(item.photoUri)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(itemImage)

            val res = itemView.resources

            name.text = item.name
            seller.text = res.getString(R.string.bid_seller_label, item.seller)
//            bids.text = res.getString(R.string.num_bids_display, item.numBids)
            currentBid.text = res.getString(R.string.current_bid, item.currentBid)
            lastBidder.text = res.getString(R.string.top_bidder_display, item.lastBidder)
            sold.visibility = when (item.isSold) {
                true -> View.VISIBLE
                else -> View.GONE
            }

            // HACK: i don't like this inside the bind view... can't think of better way to pass item
            itemView.setOnClickListener {
                auctionItemClickListener.loadBiddingFragment(item)
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.list_photo_imageView)
        var name: TextView = itemView.findViewById(R.id.list_title_textView)
        var seller: TextView = itemView.findViewById(R.id.list_seller_textView)
//        var bids: TextView = itemView.findViewById(R.id.list_bids_textView)
        var currentBid: TextView = itemView.findViewById(R.id.list_current_bid_textView)
        var lastBidder: TextView = itemView.findViewById(R.id.list_last_bidder_textView)
        var sold: ImageView = itemView.findViewById(R.id.list_sold_imageView)
    }
}