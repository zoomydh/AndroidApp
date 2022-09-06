package com.example.part11_34

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.part11_34.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var list: MutableList<ItemVO> = arrayListOf()
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        val helper = DBHelper(this)
        val db = helper.writableDatabase

        val todayCursor = db.rawQuery("select * from tb_data where date='2019-07-01'", null)
        val yesterdayCursor = db.rawQuery("select * from tb_data where date='2019-06-30'", null)
        val beforeCursor = db.rawQuery("select * from tb_data where date!='2019-06-30'", null)

        if (todayCursor.count != 0) {
            val item = HeaderItem()
            item.headerTitle = "today"
            list.add(item)
            while (todayCursor.moveToNext()) {
                val dataItem = DataItem()
                dataItem.name = todayCursor.getString(1)
                dataItem.date = todayCursor.getString(2)
                list.add(dataItem)
            }
        }

        if (yesterdayCursor.count != 0) {
            val item = HeaderItem()
            item.headerTitle = "yesterday"
            list.add(item)
            while (yesterdayCursor.moveToNext()) {
                val dataItem = DataItem()
                dataItem.name = yesterdayCursor.getString(1)
                dataItem.date = yesterdayCursor.getString(2)
                list.add(dataItem)
            }
        }

        if (beforeCursor.count != 0) {
            val item = HeaderItem()
            item.headerTitle = "before"
            list.add(item)
            while (beforeCursor.moveToNext()) {
                val dataItem = DataItem()
                dataItem.name = beforeCursor.getString(1)
                dataItem.date = beforeCursor.getString(2)
                list.add(dataItem)
            }
        }

        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewBinding.recyclerView.adapter = MyAdapter(list)
        viewBinding.recyclerView.addItemDecoration(MyDecoration())
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerView = view.findViewById<TextView>(R.id.itemHeaderView)
    }

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView = view.findViewById<TextView>(R.id.itemNameView)
        val dateView = view.findViewById<TextView>(R.id.itemDateView)
        val personView = view.findViewById<ImageView>(R.id.itemPersonView)
    }

    class MyAdapter(var list: MutableList<ItemVO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
        override fun getItemViewType(position: Int): Int {
            return list.get(position).type
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == ItemVO.TYPE_HEADER) {
                val layoutInflater = LayoutInflater.from(parent.context)
                return HeaderViewHolder(layoutInflater.inflate(R.layout.item_header, parent, false))
            } else {
                val layoutInflater = LayoutInflater.from(parent.context)
                return DataViewHolder(layoutInflater.inflate(R.layout.item_data, parent, false))
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val itemVO = list.get(position)
            if (itemVO.type == ItemVO.TYPE_HEADER) {
                val viewHolder = holder as HeaderViewHolder
                val headerItem = itemVO as HeaderItem
                viewHolder.headerView.setText(headerItem.headerTitle)
            } else {
                val viewHolder = holder as DataViewHolder
                val dataItem = itemVO as DataItem
                viewHolder.nameView.setText(dataItem.name)
                viewHolder.dateView.setText(dataItem.date)

                val count = position % 5
                when(count) {
                    0 -> (viewHolder.personView.background as GradientDrawable).setColor(0xff009688.toInt())
                    1 -> (viewHolder.personView.background as GradientDrawable).setColor(0xff4285f4.toInt())
                    2 -> (viewHolder.personView.background as GradientDrawable).setColor(0xff039be5.toInt())
                    3 -> (viewHolder.personView.background as GradientDrawable).setColor(0xff9c27b0.toInt())
                    4 -> (viewHolder.personView.background as GradientDrawable).setColor(0xff0097a7.toInt())
                }
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    inner class MyDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val index = parent.getChildAdapterPosition(view)
            val itemVO = list.get(index)
            if (itemVO.type == ItemVO.TYPE_DATA) {
                view.setBackgroundColor(0xFFFFFFFF.toInt())
                ViewCompat.setElevation(view, 10.0f)
            }
            outRect.set(20, 10, 20, 10)
        }
    }
}

abstract class ItemVO {
    abstract val type: Int
    companion object {
        val TYPE_HEADER = 0
        val TYPE_DATA = 1
    }
}

class HeaderItem : ItemVO() {
    var headerTitle : String? = null

    override val type : Int
        get() = ItemVO.TYPE_HEADER
}

class DataItem : ItemVO() {
    var name : String? = null
    var date : String? = null

    override val type: Int
        get() = ItemVO.TYPE_DATA
}