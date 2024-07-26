package com.example.newnote.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.newnote.model.NewNoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NewNoteDao {
    @Insert
    fun addNote(newNoteModel: NewNoteModel)


    /*
    Eğer Flow kullanıyorsak suspend fun gerek yok direk fun kullanabiliriz
     */
    @Query("SELECT * FROM newNote")
    fun getAllNewNote(): Flow<List<NewNoteModel>>

    @Update
    suspend fun updateNewNote(newNoteModel: NewNoteModel)

    @Delete
    suspend fun deleteNewNote(newNoteModel: NewNoteModel)
}