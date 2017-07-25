package com.webhopers.medlog.dataHolder

import android.support.v4.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webhopers.medlog.R
import com.webhopers.medlog.interfaces.ViewInterface
import com.webhopers.medlog.models.ImageModel

object DataHolder: ValueEventListener{


    private var path: String? = null
    private var urls: ArrayList<String>? = null
    private var view: ViewInterface? = null

    fun changePath(path: String) {
        this.path = path
        setListener()
    }

    fun changeView(view: ViewInterface) {
        this.view = view
    }

    fun Urls() = urls

    private fun setListener() {
        FirebaseDatabase.getInstance()
                .getReference(path)
                .addValueEventListener(this)
    }


    override fun onCancelled(error: DatabaseError?) {

    }

    override fun onDataChange(snapshot: DataSnapshot?) {
        val temp = ArrayList<String>()

        urls = null
        view?.showProgressBar(false)

        if (!snapshot!!.hasChildren()) {
            view?.getDecorView()?.background = ContextCompat.getDrawable(view?.getContext(), R.drawable.sorry_nothing_here_drawable)
            return
        } else view?.getDecorView()?.background = ContextCompat.getDrawable(view?.getContext(), R.color.colorAccent)


        for (x in snapshot!!.children) temp.add(x.getValue(ImageModel::class.java)!!.url!!)
        urls = temp
    }



}