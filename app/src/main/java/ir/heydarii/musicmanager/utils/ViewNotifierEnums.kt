package ir.heydarii.musicmanager.utils

/**
 * A class for communication of view and viewModel
 */
enum class ViewNotifierEnums {
    SHOW_LOADING, HIDE_LOADING, SAVED_INTO_DB, REMOVED_FROM_DB, EMPTY_STATE, NOT_EMPTY, ERROR_GETTING_DATA, ERROR_DATA_NOT_AVAILABLE, ERROR_SAVING_DATA, ERROR_REMOVING_DATA
}