package com.example.fnfplayground

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Character(
                     val id: UUID,
                     val soundUpId: Int,
                     val soundDownId: Int,
                     val soundRightId: Int,
                     val soundLeftId: Int,
                     val soundBId: Int,
                     val animationUpId: Int,
                     val animationDownId: Int,
                     val animationRightId: Int,
                     val animationLeftId: Int,
                     val animationBId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        UUID(1, 2),
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}