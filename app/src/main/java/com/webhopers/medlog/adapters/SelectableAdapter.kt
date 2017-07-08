package com.webhopers.medlog.adapters

import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.util.SparseArray
import android.view.*
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback
import com.bignerdranch.android.multiselector.MultiSelector
import com.bignerdranch.android.multiselector.SwappingHolder
import com.facebook.drawee.backends.pipeline.Fresco
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.squareup.picasso.Picasso
import com.stfalcon.frescoimageviewer.ImageViewer
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import com.webhopers.medlog.models.ImageModel
import com.webhopers.medlog.services.database.FirebaseDatabaseService
import com.webhopers.medlog.services.storage.FirebaseStorageService
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class SelectableAdapter(val context: Context,
                        val activity: AppCompatActivity,
                        val resources: Resources,
                        val urls: ArrayList<String>?,
                        val path: String,
                        query: Query?) :

        FirebaseRecyclerAdapter<ImageModel, SelectableAdapter.ViewHolder>(ImageModel::class.java,
                R.layout.recycler_view_item,
                ViewHolder::class.java,
                query) {



    val multiSelector = MultiSelector()
    var actMode: ActionMode? = null
    var itemViews = SparseArray<View>()
    val selectedPositions = HashSet<Int>()

    fun toggleItemSelection(select: Boolean, pos: Int) {
        if (itemViews[pos] != null) itemViews[pos].isSelected = select

        if (select)
            selectedPositions.add(pos)
        else
            selectedPositions.remove(pos)
        if (selectedPositions.isEmpty()) {
            actMode?.finish()
            return
        }
    }

    val adapterListener = object: AdapterListener {
        override fun toggleItemSelectionAdapter(select: Boolean, position: Int) {
            toggleItemSelection(select, position)
        }

        override fun getSelectedPositions(): HashSet<Int> = selectedPositions

    }

    val multiSelectorMode = object : ModalMultiSelectorCallback(multiSelector) {

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_delete -> deleteItems()
                else -> return false
            }
            return true
        }

        override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            super.onCreateActionMode(actionMode, menu)
            actMode = actionMode
            activity.menuInflater.inflate(R.menu.admin_action_menu, menu)
            return true
        }

        override fun onDestroyActionMode(actionMode: ActionMode?) {
            super.onDestroyActionMode(actionMode)
            selectedPositions.forEach {
                if (itemViews[it] != null)
                    itemViews[it].isSelected = false
            }
            selectedPositions.clear()
            actMode = null
        }

        private fun deleteItems() {

            selectedPositions.forEach {
                val model = itemViews[it].tag as ImageModel
                FirebaseStorageService.deleteFile(model.url!!)
                FirebaseDatabaseService.removeMed(model.uid!!, path)
                itemViews.put(it, null)
                notifyItemRemoved(it)
            }

            val newItems = SparseArray<View>()
            var curIndex = 0
            for (i in 0..itemViews.size() - 1) {
                if (itemViews[i] != null) {
                    newItems.put(curIndex, itemViews[i])
                    curIndex++
                }
            }

            itemViews = newItems
            actMode?.finish()
            multiSelector.isSelectable = false
            selectedPositions.clear()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = parent?.context?.inflateView(R.layout.recycler_view_item)
        return ViewHolder(context, resources, view, urls, adapterListener, activity, multiSelectorMode, multiSelector)
    }


    override fun populateViewHolder(holder: ViewHolder?, model: ImageModel?, position: Int) {
        itemViews.put(position, holder?.createView(model?.url, resources, position))
        toggleItemSelection(selectedPositions.contains(position), position)
        holder?.itemView?.tag = model

    }



    class ViewHolder(val context: Context,
                     val resources: Resources,
                     val view: View?,
                     val urls: ArrayList<String>?,
                     val adapterListener: AdapterListener,
                     val activity: AppCompatActivity,
                     val multiSelectorCallback: ModalMultiSelectorCallback,
                     val multiSelector: MultiSelector) :

            SwappingHolder(view,
                    MultiSelector()) {

        fun createView(url: String?, r: Resources, position: Int): View {
            itemView.apply {
                Picasso.with(context)
                        .load(url)
                        .resize(convertDpToPixels(100f, r).toInt(), convertDpToPixels(100f, r).toInt())
                        .centerCrop()
                        .into(recycler_view_image_view)

                setOnClickListener { viewClicked(position) }
                isLongClickable=true
                setOnLongClickListener {
                    viewLongClicked()
                    true
                }

            }
            return itemView
        }

        fun viewClicked(position: Int) {
            if (multiSelector.isSelectable) {
                val isSelected = adapterListener.getSelectedPositions().contains(layoutPosition)
                adapterListener.toggleItemSelectionAdapter(!isSelected, layoutPosition)
            } else {
                startImageViewer(position)
            }
        }

        fun viewLongClicked() {
            if (!multiSelector.isSelectable) {
                multiSelector.isSelectable = true
                activity.startSupportActionMode(multiSelectorCallback)
                adapterListener.toggleItemSelectionAdapter(true, layoutPosition)
            }
        }

        fun startImageViewer(position: Int) {
            if (urls != null) {
                ImageViewer.Builder(context, urls)
                        .setStartPosition(position)
                        .setImageMarginPx(convertDpToPixels(25f, resources).toInt())
                        .show()
                Fresco.initialize(context)
            }
        }
    }

    interface AdapterListener {
        fun toggleItemSelectionAdapter(select: Boolean, position: Int)
        fun getSelectedPositions(): HashSet<Int>
    }
}

