package ir.heydarii.musicmanager.presentation.features.savedalbums

sealed class SavedAlbumsViewState<T>(val data: T? = null) {
    class Success<T>(data: T) : SavedAlbumsViewState<T>(data)
    class Loading<T> : SavedAlbumsViewState<T>()
    class EmptyList<T> : SavedAlbumsViewState<T>()
}
