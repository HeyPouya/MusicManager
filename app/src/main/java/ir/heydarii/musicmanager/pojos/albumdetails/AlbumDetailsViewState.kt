package ir.heydarii.musicmanager.pojos.albumdetails

sealed class AlbumDetailsViewState<T> {
    data class Success<T>(val data: T) : AlbumDetailsViewState<T>()
    data class EmptyTrackList<T>(val data: T) : AlbumDetailsViewState<T>()
    class Loading<T> : AlbumDetailsViewState<T>()
    class Saved<T> : AlbumDetailsViewState<T>()
    class NotSaved<T> : AlbumDetailsViewState<T>()
}
