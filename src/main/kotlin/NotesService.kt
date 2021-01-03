class NotesService {
    private val _notesList = mutableListOf<Note>()
    val notesList: List<Note>
        get() = _notesList
    private val _commentsList = mutableListOf<Comment>()
    val commentsList: List<Comment>
        get() = _commentsList
    private val _commentsDustbin = mutableListOf<Comment>()
    val commentsDustbin: List<Comment>
        get() = _commentsDustbin
    private val notesDustbin = mutableListOf<Note>()


    fun showAllNotes() {
        if (_notesList.isEmpty()) println("You have no notes!") else println(_notesList)
    }

    fun findNoteById(id: Long): Note {
        for (item in _notesList) {
            if (item.id == id) return item
        }
        throw IllegalArgumentException("Note with the provided id doesn't exist!")
    }

    fun addNote(note: Note) {
        val noteId: Long = if (_notesList.isNotEmpty()) _notesList.last().id + 1 else 1
        note.id = noteId
        _notesList.add(note)
    }

    fun deleteNote(note: Note) {
        if (_notesList.contains(note)) {

            val iterator = _commentsList.iterator()
            while (iterator.hasNext()) {
                val oldValue = iterator.next()
                if (oldValue.noteId == note.id) iterator.remove()
            }

            _notesList.remove(note)
            println("Note \"${note.title}\" and all its comments were successfully deleted!")
        } else {
            println("You cannot delete note that doesn't exist in a notes list!")
        }
    }

    fun editNote(editedNote: Note, oldNoteId: Long) {
        for (item in _notesList) {
            if (item.id == oldNoteId) {
                editedNote.id = item.id
                editedNote.isEdited = true
                _notesList[_notesList.indexOf(item)] = editedNote
                println("Note was successfully edited")
                return
            }
        }
        println("Failed to find a note with id $oldNoteId")
    }

    fun showAllComments() {
        if (_commentsList.isEmpty()) println("You have no comments at all!") else println(_commentsList)
    }

    fun showCommentsByNote(noteId: Long) {
        var isNoteFound = false

        for (item in _notesList) {
            if (item.id == noteId) {
                isNoteFound = true
                break
            }
        }

        if (isNoteFound) {
            val commentsFoundByNote = mutableListOf<Comment>()

            for (comment in _commentsList) {
                if (comment.noteId == noteId) commentsFoundByNote.add(comment)
            }
            println(commentsFoundByNote)
        } else {
            println("A parent note for the comments with the id $noteId doesn't exist. Probably is was deleted or never created at all")
        }
    }

    fun createComment(comment: Comment) {
        var isCommentValid = false
        var idForComment: Long = 0

        for (item in _notesList) {
            if (item.id == comment.noteId) {
                isCommentValid = true
                idForComment = item.id
                break
            }
        }
        if (isCommentValid) {
            comment.noteId = idForComment
            comment.commentId = if(_commentsList.isEmpty()) 1 else _commentsList.last().commentId + 1
            _commentsList.add(comment)
            comment.commentIndex = _commentsList.lastIndex
        } else print("You cannot create a comment for a non-existing note!")
    }

    fun deleteComment(commentId: Long) {
        for (item in _commentsList) {
            if (item.commentId == commentId) {
                _commentsDustbin.add(item)
                item.commentIndex = _commentsList.indexOf(item)
                _commentsList.remove(item)
                println("Comment was successfully moved to the dustbin! You can restore it if you want " +
                        "using the id $commentId")
                return
            }
        }
        println("Failed to find a comment for deletion")
        }


    fun restoreComment(commentID: Long) {
        for (item in _commentsDustbin) {
            if (item.commentId == commentID){
                _commentsList.add(item.commentIndex, item)
                _commentsDustbin.remove(item)
                println("The removed comment was restored and index ${item.commentIndex + 1}, as if it was never deleted")
                return
            }
        }
        println("Failed to find a comment to restore")
        }

    fun editComment(editedComment: Comment, commentToReplaceId: Long) {
        for (item in _commentsList) {
            if (item.commentId == commentToReplaceId && item.noteId == editedComment.noteId) {
                editedComment.commentIndex = item.commentIndex
                editedComment.isEdited = true
                _commentsList[_commentsList.indexOf(item)] = editedComment
                println("Comment was successfully edited")
                return
            }
        }
        println("Failed to find a matchable comment to edit")
    }
}