package com.example.fnfplayground

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ImageView

class CharactersImageAdapter(private val context: Context) : BaseAdapter() {
    var arrayListCharactersImage : Array<String> = context.assets.list("images/icons") as Array<String>
    private val icons = Array(arrayListCharactersImage.size, init = {BitmapDrawable(context.resources)})

    init {
        for (i in arrayListCharactersImage.indices){
            val inputStream = context.assets
                .open("images/icons/${arrayListCharactersImage[i]}")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            icons[i] = BitmapDrawable(context.resources, bitmap)
        }
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
        imageView.setImageDrawable(icons[position] as Drawable)
        imageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 150)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER

        return imageView
    }

}
