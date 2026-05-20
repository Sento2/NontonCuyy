package com.kel6.nontoncuyy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kel6.nontoncuyy.R
import com.google.android.material.imageview.ShapeableImageView

data class Cast(val name: String, val role: String, val imageUrl: String? = null)

class CastAdapter(private val castList: List<Cast>) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCastImage: ShapeableImageView = view.findViewById(R.id.ivCastImage)
        val tvCastName: TextView = view.findViewById(R.id.tvCastName)
        val tvCastRole: TextView = view.findViewById(R.id.tvCastRole)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = castList[position]
        holder.tvCastName.text = cast.name
        holder.tvCastRole.text = cast.role
        // imageUrl loading logic can be added here if available
    }

    override fun getItemCount(): Int = castList.size
}