package com.coding.tawktoexam.dao

import androidx.room.*
import com.coding.tawktoexam.entity.Note
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(updatedNote: Note): Completable

    @Query("SELECT * FROM tbl_note WHERE tbl_note.id = :userId")
    fun getNote(userId: Int): Flowable<Note>
}