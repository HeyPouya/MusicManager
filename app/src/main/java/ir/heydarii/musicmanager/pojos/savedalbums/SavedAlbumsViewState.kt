package ir.heydarii.musicmanager.pojos.savedalbums

sealed class SavedAlbumsViewState<T> {
    data class Success<T>(val data: T) : SavedAlbumsViewState<T>()
    class Loading<T> : SavedAlbumsViewState<T>()
    class EmptyList<T> : SavedAlbumsViewState<T>()
}
