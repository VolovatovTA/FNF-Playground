package com.example.fnfplayground

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.ImageView

class CharactersImageAdapter(private val context: Context) : BaseAdapter() {
    var arrayListCharactersImage = emptyArray<Int>()
    init {
        arrayListCharactersImage = arrayOf(R.drawable.icon1char, R.drawable.icon1char,
            R.drawable.icon1char, R.drawable.icon1char, R.drawable.icon1char, R.drawable.icon1char )
    }
    override fun getCount(): Int {
        return arrayListCharactersImage.size
    }

    override fun getItem(position: Int): Any {
        return arrayListCharactersImage[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val imageView = ImageView(context)
        imageView.setImageResource(arrayListCharactersImage[position])
        imageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 150)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER

        return imageView
    }

}
