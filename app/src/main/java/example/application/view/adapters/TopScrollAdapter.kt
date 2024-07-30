package example.application.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import example.application.model.UseData
import example.application.R

class TopScrollAdapter(private val buttonItems: ArrayList<UseData.TopScroll>, private val clickListener: OnItemClickListener): RecyclerView.Adapter<TopScrollAdapter.TopButtonViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class TopButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.top_bar_textView)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopButtonViewHolder {
        val topBar = LayoutInflater.from(parent.context).inflate(R.layout.top_scroll, parent, false)
        return TopButtonViewHolder(topBar)
    }

    override fun onBindViewHolder(holder: TopButtonViewHolder, position: Int) {
        val currentItem = buttonItems[position]

        holder.textView.text = currentItem.text

        holder.textView.setOnClickListener {
            clickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return buttonItems.size
    }

}