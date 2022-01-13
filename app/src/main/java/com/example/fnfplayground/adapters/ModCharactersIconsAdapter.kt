package com.example.fnfplayground.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ImageView

class ModCharactersIconsAdapter(private val context: Context) : BaseAdapter() {
    val PATH = "shared/mod characters"
    var arrayListCharactersFolders : Array<String> = context.assets.list(PATH) as Array<String>
    private val icons = Array(arrayListCharactersFolders.size, init = { BitmapDrawable(context.resources) })

    init {
        Log.d("DebugAnimation", "icons = ${icons.size}")

        for (i in arrayListCharactersFolders.indices){
            val nameIcon : Array<String> = context.assets.list("$PATH/${arrayListCharactersFolders[i]}/icon") as Array<String>

            val inputStream = context.assets
                .open("$PATH/${arrayListCharactersFolders[i]}/icon/${nameIcon[0]}")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            icons[i] = BitmapDrawable(context.resources, bitmap)
        }
    }
    override fun getCount(): Int {
        return arrayListCharactersFolders.size
    }

    override fun getItem(position: Int): Any {
        return arrayListCharactersFolders[position]
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