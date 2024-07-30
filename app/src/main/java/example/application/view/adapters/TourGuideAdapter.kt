package example.application.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import example.application.R
import example.application.model.network.response.guides.GuidesResponseItem
import example.application.model.network.response.services.Service

class TourGuideAdapter: RecyclerView.Adapter<TourGuideAdapter.TourGuideViewHolder>() {


    inner class TourGuideViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.tourGuidePicture)
        val textView: TextView = itemView.findViewById(R.id.tourGuideAbout)
        val textView1: TextView = itemView.findViewById(R.id.tourGuideName)
        val textView2: TextView = itemView.findViewById(R.id.tourGuideid)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): TourGuideAdapter.TourGuideViewHolder {
        val tourData = LayoutInflater.from(parent.context).inflate(R.layout.tour_guide_item, parent,false)
        return TourGuideViewHolder(tourData)
    }
    private val differCallBackStack = object : DiffUtil.ItemCallback<GuidesResponseItem>() {
        override fun areItemsTheSame(oldItem: GuidesResponseItem, newItem: GuidesResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GuidesResponseItem, newItem: GuidesResponseItem): Boolean {
            return false
        }
    }

    val differ = AsyncListDiffer(this, differCallBackStack)

    override fun onBindViewHolder(holder: TourGuideAdapter.TourGuideViewHolder, position: Int) {
        val tourData = differ.currentList[position]
        holder.itemView.apply {

            holder.textView.text = tourData.about
            holder.textView1.text = tourData.guide_name
            holder.textView2.text = tourData.county_id

            Glide.with(this)
                .load(tourData.picture.toString())
                .apply(RequestOptions()
                    .placeholder(R.drawable.image_background)
                    .error(com.google.android.material.R.drawable.mtrl_ic_error)
                )
                .into(holder.imageView)


        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}