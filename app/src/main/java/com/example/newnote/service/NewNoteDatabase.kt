package com.example.newnote.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newnote.model.NewNoteModel
/*
exportSchema parametresi, Room'un veritabanı şemasını bir dosyaya dışa aktarmasını sağlar.
 Bu özellik, özellikle veritabanı şema değişikliklerini takip etmek ve versiyon kontrol sistemlerinde
 (örneğin Git) şema değişikliklerini izlemek için kullanışlıdır
 */
@Database(entities = [NewNoteModel::class], version = 1,exportSchema = false)
abstract class NewNoteDatabase : RoomDatabase() {
    abstract fun newNoteDao():NewNoteDao


    companion object{


        @Volatile
        private var instance :NewNoteDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock = lock){
            instance ?: databaseCreate(context).also {
                instance = it
            }
        }

        private fun databaseCreate(context: Context) = Room.databaseBuilder(
            context.applicationContext,NewNoteDatabase::class.java,"newNoteDatabase"
        ).build()
    }
}