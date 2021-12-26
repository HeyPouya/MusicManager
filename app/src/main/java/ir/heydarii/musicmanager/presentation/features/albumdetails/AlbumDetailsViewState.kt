package ir.heydarii.musicmanager.presentation.features.albumdetails

sealed class AlbumDetailsViewState<T>(val data: T? = null) {
    class Success<T>(data: T) : AlbumDetailsViewState<T>(data)
    class EmptyTrackList<T>(data: T) : AlbumDetailsViewState<T>(data)
    class Loading<T> : AlbumDetailsViewState<T>()
    class Saved<T> : AlbumDetailsViewState<T>()
    class NotSaved<T> : AlbumDetailsViewState<T>()
}
