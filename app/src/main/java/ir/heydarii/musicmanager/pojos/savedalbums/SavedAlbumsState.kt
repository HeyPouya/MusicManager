package ir.heydarii.musicmanager.pojos.savedalbums

sealed class SavedAlbumsState<T> {
    data class Success<T>(val data: T) : SavedAlbumsState<T>()
    class Loading<T> : SavedAlbumsState<T>()
}
