data class Comment(
    var noteId: Long,
    val message: String,
    var commentIndex: Int = 0,          // used only to restore a previously deleted comment
    var isEdited: Boolean = false       // changes its state only when edited at least once
) {

}