package com.example.newnote.viewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.newnote.model.NewNoteModel
import com.example.newnote.service.NewNoteDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(application: Application) :BaseViewModel(application) {

    private val _newNotes : MutableStateFlow<List<NewNoteModel>> = MutableStateFlow(emptyList())
    val notes = _newNotes.asStateFlow()

    init {
        getNotes()
    }





    private fun getNotes(){
        viewModelScope.launch (Dispatchers.IO){
            val dao = NewNoteDatabase(getApplication()).newNoteDao()

            dao.getAllNewNote().collect { list->
                _newNotes.value = list

            }
        }
    }


    fun updateNote(noteModel: NewNoteModel){
        viewModelScope.launch (Dispatchers.IO){
            val dao = NewNoteDatabase(getApplication()).newNoteDao()
            dao.updateNewNote(noteModel)
        }
    }


    fun deleteNote(noteModel: NewNoteModel){
        viewModelScope.launch (Dispatchers.IO){
            val dao = NewNoteDatabase(getApplication()).newNoteDao()
            dao.deleteNewNote(noteModel)
        }
    }

    fun addNote(noteModel: NewNoteModel){
        viewModelScope.launch (Dispatchers.IO){
            val dao = NewNoteDatabase(getApplication()).newNoteDao()
            dao.addNote(noteModel)
        }
    }

}