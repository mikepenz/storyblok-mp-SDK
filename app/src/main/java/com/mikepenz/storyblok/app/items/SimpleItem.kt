package com.mikepenz.storyblok.app.items

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.storyblok.app.R
import com.mikepenz.storyblok.app.databinding.SampleItemBinding

/**
 * Created by mikepenz on 28.12.15.
 */
class SimpleItem : AbstractItem<SimpleItem.ViewHolder>() {

    private var name: String? = null
    private var description: String? = null

    /**
     * defines the type defining this item. must be unique. preferably an id
     *
     * @return the type
     */
    override val type: Int
        get() = R.id.sample_item

    /**
     * defines the layout which will be used for this item in the list
     *
     * @return the layout for this item
     */
    override val layoutRes: Int
        get() = R.layout.sample_item

    fun withName(name: String?): SimpleItem {
        this.name = name
        return this
    }

    fun withDescription(description: String?): SimpleItem {
        this.description = description
        return this
    }

    /**
     * binds the data of this item onto the viewHolder
     *
     * @param holder the viewHolder of this item
     */
    override fun bindView(holder: ViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)
        holder.name.text = name
        holder.description.text = description
    }

    override fun unbindView(holder: ViewHolder) {
        super.unbindView(holder)
        holder.name.text = null
        holder.description.text = null
    }

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    /**
     * our ViewHolder
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = SampleItemBinding.bind(view)
        var name: TextView = binding.name
        var description: TextView = binding.description
    }
}
