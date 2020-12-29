data class Note(
    var id: Long = 0, // reassigned later in NotesService
    val title: String,
    val text: String,
    var isEdited: Boolean = false // changes its state only if a note is edited at least once
) {
}