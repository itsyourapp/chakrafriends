package app.itsyour.chakra.android.feature.main.logout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.itsyour.chakra.android.R
import app.itsyour.chakra.android.feature.login.LoginActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.TimeUnit

class LogoutActivity : AppCompatActivity() {

    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        subscriptions += Observable.timer(1500, TimeUnit.MILLISECONDS).subscribe(::timeout)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun timeout(ignored: Long) {
        LoginActivity.navigateTo(this)
        finish()
    }
}