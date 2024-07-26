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
import example.application.model.network.response.services.Image
import example.application.model.network.response.services.Service
import example.application.view.AccommodationFragment

class AccommodationAdapter(private val clickListener: OnAccommodationClickListener): RecyclerView.Adapter<AccommodationAdapter.AccommodationViewHolder>() {

    interface OnAccommodationClickListener {
        fun onAccommodationClick(position: Int, viewId: Int)
    }


    class AccommodationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.serviceImage)
        val textView: TextView = itemView.findViewById(R.id.serviceId)
        val textView1: TextView = itemView.findViewById(R.id.serviceImageId)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccommodationViewHolder {
        val serviceImages = LayoutInflater.from(parent.context).inflate(R.layout.accommodation_viewholder, parent, false)
        return AccommodationViewHolder(serviceImages)
    }

    private val differCallBackStack = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return false
        }
    }

    val differ = AsyncListDiffer(this, differCallBackStack)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AccommodationViewHolder, position: Int) {
        val imageHolder = differ.currentList[position]
        holder.itemView.apply {

            holder.textView.text = imageHolder.image_id.toString()
            holder.textView1.text = imageHolder.service_id.toString()

            if (imageHolder.service_image.isNotEmpty()) {
                val image = imageHolder.service_image
                Glide.with(this)
                    .load(image)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.image_background)
                        .error(com.google.android.material.R.drawable.mtrl_ic_error))
                    .into(holder.imageView)
            }
            holder.imageView.setOnClickListener{
                clickListener.onAccommodationClick(position, it.id)
            }
        }
    }
}