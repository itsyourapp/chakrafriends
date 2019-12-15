package app.itsyour.chakra.android.feature.main

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import app.itsyour.chakra.android.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    companion object {
        fun navigateTo(context: Context)
            = context.startActivity(Intent(context, MainActivity::class.java).apply {
                flags = FLAG_ACTIVITY_CLEAR_TASK })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
