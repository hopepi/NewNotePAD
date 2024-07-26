package com.example.newnote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date


@Entity("newNote")
data class NewNoteModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo("title")
    val title : String,
    @ColumnInfo("subTitle")
    val subTitle : String,
    @ColumnInfo("done")
    val done : Boolean = false,
    @ColumnInfo("added")
    val added : Long = System.currentTimeMillis()
)


val NewNoteModel.addDate : String get() = SimpleDateFormat("yyyy/MM/dd hh:mm").format(Date(added))