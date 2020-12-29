fun main() {

    val notesService = NotesService()

    val firstNote = Note(title = "First note", text = "Hey!")
    val secondNote = Note(title = "Second note", text = "Lol kek")

    notesService.addNote(firstNote)
    notesService.addNote(secondNote)

    val commentForFirstNote = Comment(noteId = 1, message = "That's a great note there!")
    val secondCommentForFirstNote = Comment(noteId = 1, message = "Cannot but agree with this note!")
    val commentForSecondNote = Comment(noteId = 2, message = "This note is even better than the first one!")
    val secondCommentForSecondNote = Comment(noteId = 2, message = "Wow! This note just blew my mind!")




    notesService.showAllNotes()

    notesService.createComment(commentForFirstNote)
    notesService.createComment(secondCommentForFirstNote)
    notesService.createComment(commentForSecondNote)
    notesService.createComment(secondCommentForSecondNote)

    notesService.showCommentsByNote(1)

    notesService.deleteNote(firstNote)

    notesService.showAllComments()


}