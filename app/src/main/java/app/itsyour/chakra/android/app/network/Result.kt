package app.itsyour.chakra.android.app.network


/**
 * A generic class that holds a value with its loading status.
 *
 *  @author https://github.com/android/architecture-samples
 */
sealed class Result<out R> {

    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[value=$value]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && value != null
