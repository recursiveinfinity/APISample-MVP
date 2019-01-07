package com.example.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apidemo_mvp.R
import com.example.apidemo_mvp.model.Response
import kotlinx.android.synthetic.main.item_pass.view.*

class ISSAdapter : RecyclerView.Adapter<ISSAdapter.ISSViewHolder>() {

    private val data = arrayListOf<Response>()


    fun setData(items: List<Response>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ISSViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pass, parent, false)
        return ISSViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ISSViewHolder, position: Int) {
        viewHolder.bind(data[position])
    }


    class ISSViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(response: Response) {
            view.tvDate.text = response.risetime.toString()
            view.tvTime.text = response.duration.toString()
        }

    }
}