package example.eclestay.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import example.application.R
import example.application.model.network.response.services.Service

class ServiceAdapter (private val listener: OnItemClickListener): RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {


    interface OnItemClickListener{
        fun onBookNowClick(service: Service)
    }
    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.picImage)
        val imageView1: ImageView = itemView.findViewById(R.id.imageView1)
        val imageView2: ImageView = itemView.findViewById(R.id.imageView2)
        val imageView3: ImageView = itemView.findViewById(R.id.imageView3)
        val imageView4: ImageView = itemView.findViewById(R.id.imageView4)

        val textView: TextView = itemView.findViewById(R.id.county)
        val textView1: TextView = itemView.findViewById(R.id.numberOfBeds)
        val textView2: TextView = itemView.findViewById(R.id.hostName)
        val textView3: TextView = itemView.findViewById(R.id.startDate)
        val textView4: TextView = itemView.findViewById(R.id.endDate)
        val textView5: TextView = itemView.findViewById(R.id.tvDescription)
        val textView6: TextView = itemView.findViewById(R.id.availability)
        val textView7: TextView = itemView.findViewById(R.id.serviceName)
        val textView8: TextView = itemView.findViewById(R.id.price)

        private val bookNowButton: Button = itemView.findViewById(R.id.book_now)

        fun bind(service: Service) {
            bookNowButton.setOnClickListener{
                listener.onBookNowClick(service)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val accommodations = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(accommodations)
    }

    private val differCallBackStack = object : DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return false
        }
    }

    val differ = AsyncListDiffer(this, differCallBackStack)

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val accommodationItem = differ.currentList[position]
        holder.bind(accommodationItem)
        holder.itemView.apply {

            val county = accommodationItem.county.countyId
                holder.textView.text = county

            holder.textView1.text = accommodationItem.beds.toString()
            val hosts = accommodationItem.hosts.host_name
            holder.textView2.text = hosts.toString()
            holder.textView3.text = accommodationItem.start_date.toString()
            holder.textView4.text = accommodationItem.end_date.toString()
            holder.textView5.text = accommodationItem.description
            holder.textView7.text = accommodationItem.service_name
            holder.textView6.text = accommodationItem.approved.toString()
            holder.textView8.text = accommodationItem.price.toString()

            if (accommodationItem.images.isNotEmpty() ) {

                val images = accommodationItem.images[0].service_image.toString()

                    Glide.with(this)
                        .load(images[0])
                        .apply(RequestOptions()
                            .placeholder(R.drawable.image_background)
                            .error(com.google.android.material.R.drawable.mtrl_ic_error)
                        )
                        .into(holder.imageView)

                    Glide.with(this)
                        .load(images[1])
                        .apply(RequestOptions()
                            .placeholder(R.drawable.image_background)
                            .error(com.google.android.material.R.drawable.mtrl_ic_error)
                        )
                        .into(holder.imageView1)

                    Glide.with(this)
                        .load(images[2])
                        .apply(RequestOptions()
                            .placeholder(R.drawable.image_background)
                            .error(com.google.android.material.R.drawable.mtrl_ic_error)
                        )
                        .into(holder.imageView2)

                    Glide.with(this)
                        .load(images[3])
                        .apply(RequestOptions()
                            .placeholder(R.drawable.image_background)
                            .error(com.google.android.material.R.drawable.mtrl_ic_error)
                        )
                        .into(holder.imageView3)


                    Glide.with(this)
                        .load(images[4])
                        .apply(RequestOptions()
                            .placeholder(R.drawable.image_background)
                            .error(com.google.android.material.R.drawable.mtrl_ic_error)
                        )
                        .into(holder.imageView4)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
