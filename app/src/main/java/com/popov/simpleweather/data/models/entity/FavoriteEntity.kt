package com.popov.simpleweather.data.models.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PlaceEntity::class,
            parentColumns = ["id"],
            childColumns = ["idFavorite"],
            deferred = true,
            onDelete = ForeignKey.CASCADE
        )], tableName = "favorite"
)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "idFavorite")
    val id: String
) : Parcelable