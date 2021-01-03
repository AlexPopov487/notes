import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalArgumentException

class NotesServiceTest {
    val notesService = NotesService()

    @Test
    fun findNoteById_success(){
        val firstNote = Note(title = "First note", text = "Hey!")

        notesService.addNote(firstNote)

        val result = notesService.findNoteById(1)

        assertEquals(firstNote, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun findNoteById_throwException(){
        val firstNote = Note(title = "First note", text = "Hey!")

        notesService.addNote(firstNote)

        notesService.findNoteById(2)
    }

    @Test
    fun addNote_ToEmptyList(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val expected:Boolean = firstNote.id == 1L

        assertTrue(expected)
    }

    @Test
    fun addNote_ToFilledList(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val secondNote = Note(title = "Second note", text = "Hey!")
        notesService.addNote(secondNote)
        val expected:Boolean = secondNote.id == 2L

        assertTrue(expected)
    }

    @Test
    fun deleteNote_success(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)
        val secondNote = Note(title = "Second note", text = "Hey!")
        notesService.addNote(secondNote)

        notesService.deleteNote(secondNote)

        val expected = notesService.notesList.contains(secondNote)

        assertFalse(expected)
    }

    @Test
    fun deleteNote_noNoteToDelete(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)
        val secondNote = Note(title = "Second note", text = "Hey!")

        notesService.deleteNote(secondNote)
        val expected = notesService.notesList.contains(secondNote)

        assertFalse(expected)
    }

    @Test
    fun editNote_success(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)
        val secondNote = Note(title = "Second note", text = "Hey!")
        notesService.addNote(secondNote)
        val thirdNote = Note(title = "Note to replace the old one", text = "Luls")

        notesService.editNote(thirdNote, 2)

        val expected = notesService.notesList[1].isEdited && notesService.notesList.contains(thirdNote)

        assertTrue(expected)
    }

    @Test
    fun editNote_noNoteToEdit(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)
        val secondNote = Note(title = "Second note", text = "Hey!")
//        notesService.addNote(secondNote)
        val thirdNote = Note(title = "Note to replace the old one", text = "Luls")

        notesService.editNote(thirdNote, 2)

        val expected = notesService.notesList.contains(thirdNote)

        assertFalse(expected)
    }

    @Test
    fun createComment_success(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val commentForFirstNote = Comment(noteId = 1, message = "That's a great note there!")

        notesService.createComment(commentForFirstNote)

        val expected = notesService.commentsList.contains(commentForFirstNote)

        assertTrue(expected)
    }

    @Test
    fun createComment_commentIdDoesntMatchNoteId(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val commentForFirstNote = Comment(noteId = 2, message = "That's a great note there!")

        notesService.createComment(commentForFirstNote)

        val expected = notesService.commentsList.contains(commentForFirstNote)

        assertFalse(expected)
    }

    @Test
    fun deleteComment_successful(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val commentForFirstNote = Comment(noteId = 1, message = "That's a great note there!")
        notesService.createComment(commentForFirstNote)
        notesService.deleteComment(1)

        val expected = !notesService.commentsList.contains(commentForFirstNote)

        assertTrue(expected)
    }

    @Test
    fun deleteComment_noCommentToDelete(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val commentForFirstNote = Comment(noteId = 1, message = "That's a great note there!")
//        notesService.createComment(commentForFirstNote)
        notesService.deleteComment(1)

        val expected = !notesService.commentsDustbin.contains(commentForFirstNote)

        assertTrue(expected)
    }

    @Test
    fun restoreComment_successful(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val commentForFirstNote = Comment(noteId = 1, message = "That's a great note there!")
        notesService.createComment(commentForFirstNote)
        notesService.deleteComment(1)

        val expectedBeforeRestored = notesService.commentsDustbin.contains(commentForFirstNote)

        notesService.restoreComment(1)

        val expectedAfterRestored = !notesService.commentsDustbin.contains(commentForFirstNote) &&
                notesService.commentsList.contains(commentForFirstNote)


        assertTrue(expectedBeforeRestored)
        assertTrue(expectedAfterRestored)
    }

    @Test
    fun restoreComment_noCommentInDustbin(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val commentForFirstNote = Comment(noteId = 1, message = "That's a great note there!")
        notesService.createComment(commentForFirstNote)
//        notesService.deleteComment(commentForFirstNote)

        val expected = !notesService.commentsDustbin.contains(commentForFirstNote)

        notesService.restoreComment(1)

        assertTrue(expected)
    }

    @Test
    fun editNote_successful(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val commentForFirstNote = Comment(noteId = 1, message = "That's a great note there!")
        notesService.createComment(commentForFirstNote)

        val newComment = Comment(noteId= 1, message= "I'm here to replace the first comment")

        notesService.editComment(newComment, 1)

        val expected = notesService.commentsList[0].isEdited

        assertTrue(expected)
    }

    @Test
    fun editNote_commentIdsDontMatch(){
        val firstNote = Note(title = "First note", text = "Hey!")
        notesService.addNote(firstNote)

        val commentForFirstNote = Comment(noteId = 1, message = "That's a great note there!")
        notesService.createComment(commentForFirstNote)

        val newComment = Comment(noteId= 2, message= "I'm here to replace the first comment")

        notesService.editComment(newComment, 2)

        val expected = !notesService.commentsList[0].isEdited

        assertTrue(expected)
    }
}