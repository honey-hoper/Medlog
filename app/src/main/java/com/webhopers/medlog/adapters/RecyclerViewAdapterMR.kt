package com.webhopers.medlog.adapters

import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback
import com.bignerdranch.android.multiselector.MultiSelector
import com.bignerdranch.android.multiselector.SwappingHolder
import com.facebook.drawee.backends.pipeline.Fresco
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.squareup.picasso.Picasso
import com.stfalcon.frescoimageviewer.ImageViewer
import com.webhopers.medlog.R
import com.webhopers.medlog.dataHolder.DataHolder
import com.webhopers.medlog.extensions.inflateView
import com.webhopers.medlog.medRepMain.MedRepMainView
import com.webhopers.medlog.models.Image
import com.webhopers.medlog.models.ImageModel
import com.webhopers.medlog.models.Playlist
import com.webhopers.medlog.services.database.RealmDatabaseService
import com.webhopers.medlog.utils.convertDpToPixels
import io.realm.RealmList
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class RecyclerViewAdapterMR(val view: MedRepMainView,
                            val context: Context,
                            val activity: AppCompatActivity,
                            val resources: Resources,
                            query: Query?)
            : FirebaseRecyclerAdapter<ImageModel, RecyclerViewAdapterMR.ViewHolderMR>(ImageModel::class.java,
                R.layout.recycler_view_item,
                ViewHolderMR::class.java,
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

    val adapterListener = object: RecyclerViewAdapterMR.AdapterListener {
        override fun toggleItemSelectionAdapter(select: Boolean, position: Int) {
            toggleItemSelection(select, position)
        }

        override fun getSelectedPositions(): HashSet<Int> = selectedPositions

    }

    val multiSelectorMode = object : ModalMultiSelectorCallback(multiSelector) {

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_create_playlist -> createPlaylist()
                else -> return false
            }
            return true
        }

        override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            super.onCreateActionMode(actionMode, menu)
            actMode = actionMode
            activity.menuInflater.inflate(R.menu.med_rep_action_menu, menu)
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
    }

    fun createPlaylist(playlistName: String = "test1") {

        val urls = RealmList<Image>()
        selectedPositions.forEach {
            val model = itemViews[it].tag as ImageModel
            urls.add(Image(model.url!!))
        }

        actMode?.finish()
        multiSelector.isSelectable = false
        selectedPositions.clear()

        val playlist = Playlist(playlistName, urls)
        try {
            RealmDatabaseService.createPlaylist(playlist)
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show()
        } catch (e: RealmPrimaryKeyConstraintException) {
            println("Primary Key Exists")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolderMR {
        val view = context.inflateView(R.layout.recycler_view_item)
        return ViewHolderMR(view, context, resources, adapterListener, activity, multiSelectorMode, multiSelector)
    }

    override fun populateViewHolder(holder: ViewHolderMR?, model: ImageModel?, position: Int) {
        view.showProgressBar(false)
        itemViews.put(position, holder?.createView(model?.url,position))
        toggleItemSelection(selectedPositions.contains(position), position)
        holder?.itemView?.tag = model
    }


    class ViewHolderMR(val view: View?,
                       val context: Context,
                       val resources: Resources,
                       val adapterListener: AdapterListener,
                       val activity: AppCompatActivity,
                       val multiSelectorCallback: ModalMultiSelectorCallback,
                       val multiSelector: MultiSelector)

        : SwappingHolder(view, MultiSelector()) {

        fun createView(url: String?, position: Int): View {
            itemView.apply {
                Picasso.with(context)
                        .load(url)
                        .resize(convertDpToPixels(100f, resources).toInt(), convertDpToPixels(100f, resources).toInt())
                        .centerCrop()
                        .into(recycler_view_image_view)
                setOnClickListener { viewClicked(position) }
                isLongClickable = true
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
            val urls = DataHolder.Urls()
            if (urls == null)
                return

            ImageViewer.Builder(context, urls)
                    .setStartPosition(position)
                    .setImageMarginPx(convertDpToPixels(25f, resources).toInt())
                    .show()
            Fresco.initialize(context)

        }

    }

    interface AdapterListener {
        fun toggleItemSelectionAdapter(select: Boolean, position: Int)
        fun getSelectedPositions(): HashSet<Int>
    }

}