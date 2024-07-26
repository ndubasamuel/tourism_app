package example.application.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import example.application.model.UseData
import example.application.R

class CabsAdapter(private val cabScroll: ArrayList<UseData.CabScroll>): RecyclerView.Adapter<CabsAdapter.CabsViewHolder>() {



    inner class CabsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabsAdapter.CabsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cabs_scroll_viewholder, parent, false)
        return CabsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CabsAdapter.CabsViewHolder, position: Int) {
        val cabScroll = cabScroll[position]
        holder.itemView.apply {
            holder.textView.text = cabScroll.text
        }
    }

    override fun getItemCount(): Int {
        return cabScroll.size
    }

}