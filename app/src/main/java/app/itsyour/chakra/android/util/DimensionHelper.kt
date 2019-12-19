package app.itsyour.chakra.android.util

import android.content.Context
import android.util.TypedValue

object DimensionsHelper {
    fun dpToPx(context: Context, dp: Float) = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
}