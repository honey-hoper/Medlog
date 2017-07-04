package com.webhopers.medlog.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import kotlinx.android.synthetic.main.exp_list_child_item_mr.view.*
import kotlinx.android.synthetic.main.exp_list_grp_itm_mr.view.*

class ExpandableListAdapter(val context: Context,
                            val header: ArrayList<String>,
                            val items: HashMap<String, List<String>>): BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int) = header[groupPosition]


    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    override fun hasStableIds() = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var v: View? = null
        if (convertView == null)
            v = context.inflateView(R.layout.exp_list_grp_itm_mr)
        else
            v = convertView

        if (v != null)
            v.group_item.text = header[groupPosition]

        return v
    }

    override fun getChildrenCount(groupPosition: Int) = items[header[groupPosition]]!!.size

    override fun getChild(groupPosition: Int, childPosition: Int) = items[header[groupPosition]]!![childPosition]

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view: View? = null
        if (convertView == null) view = context.inflateView(R.layout.exp_list_child_item_mr)
        else view = convertView

        if (view != null) {
            view.list_item.text = items[header[groupPosition]]!![childPosition]
        }
        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun getGroupCount() = header.size

}