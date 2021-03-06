class NotesService {
    val notesList = mutableListOf<Note>()
    val commentsList = mutableListOf<Comment>()
    val commentsDustbin = mutableListOf<Comment>()
    val notesDustbin = mutableListOf<Note>()


    fun showAllNotes() {
        if (notesList.isEmpty()) println("You have no notes!") else println(notesList)
    }

    fun findNoteById(id: Long): Note {
        for (item in notesList) {
            if (item.id == id) return item
        }
        throw IllegalArgumentException("Note with the provided id doesn't exist!")
    }

    fun addNote(note: Note) {
        val noteId: Long = if (notesList.isNotEmpty()) notesList.last().id + 1 else 1
        note.id = noteId
        notesList.add(note)
    }

    fun deleteNote(note: Note) {
        if (notesList.contains(note)) {

            val iterator = commentsList.iterator()
            while (iterator.hasNext()) {
                val oldValue = iterator.next()
                if (oldValue.noteId == note.id) iterator.remove()
            }

            notesList.remove(note)
            println("Note \"${note.title}\" and all its comments were successfully deleted!")
        } else {
            println("You cannot delete note that doesn't exist in a notes list!")
        }
    }

    fun editNote(editedNote: Note, oldNoteId: Long) {
        for (item in notesList) {
            if (item.id == oldNoteId) {
                editedNote.id = item.id
                editedNote.isEdited = true
                notesList[notesList.indexOf(item)] = editedNote
                println("Note was successfully edited")
                return
            }
        }
        println("Failed to find a note with id $oldNoteId")
    }

    fun showAllComments() {
        if (commentsList.isEmpty()) println("You have no comments at all!") else println(commentsList)
    }

    fun showCommentsByNote(noteId: Long) {
        var isNoteFound = false

        for (item in notesList) {
            if (item.id == noteId) {
                isNoteFound = true
                break
            }
        }

        if (isNoteFound) {
            val commentsFoundByNote = mutableListOf<Comment>()

            for (comment in commentsList) {
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

        for (item in notesList) {
            if (item.id == comment.noteId) {
                isCommentValid = true
                idForComment = item.id
                break
            }
        }
        if (isCommentValid) {
            comment.noteId = idForComment
            comment.commentId = if(commentsList.isEmpty()) 1 else commentsList.last().commentId + 1
            commentsList.add(comment)
            comment.commentIndex = commentsList.lastIndex
        } else print("You cannot create a comment for a non-existing note!")
    }

    fun deleteComment(commentId: Long) {
        for (item in commentsList) {
            if (item.commentId == commentId) {
                commentsDustbin.add(item)
                item.commentIndex = commentsList.indexOf(item)
                commentsList.remove(item)
                println("Comment was successfully moved to the dustbin! You can restore it if you want " +
                        "using the id $commentId")
                return
            }
        }
        println("Failed to find a comment for deletion")
        }


    fun restoreComment(commentID: Long) {
        for (item in commentsDustbin) {
            if (item.commentId == commentID){
                commentsList.add(item.commentIndex, item)
                commentsDustbin.remove(item)
                println("The removed comment was restored and index ${item.commentIndex + 1}, as if it was never deleted")
                return
            }
        }
        println("Failed to find a comment to restore")
        }

    fun editComment(editedComment: Comment, commentToReplaceId: Long) {
        for (item in commentsList) {
            if (item.commentId == commentToReplaceId && item.noteId == editedComment.noteId) {
                editedComment.commentIndex = item.commentIndex
                editedComment.isEdited = true
                commentsList[commentsList.indexOf(item)] = editedComment
                println("Comment was successfully edited")
                return
            }
        }
        println("Failed to find a matchable comment to edit")
    }
}