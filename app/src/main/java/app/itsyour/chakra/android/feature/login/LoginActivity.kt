package app.itsyour.chakra.android.feature.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import app.itsyour.chakra.android.R
import app.itsyour.chakra.android.feature.main.MainActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<LoginViewModel> { factory }

    private val subscriptions = CompositeDisposable()

    private lateinit var loginEnabled: Observable<Boolean>
    private lateinit var emailObs: Observable<TextViewAfterTextChangeEvent>
    private lateinit var passwordObs: Observable<TextViewAfterTextChangeEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        observeViewModel()
        observeCredentials()
        observeLoginButton()
    }

    private fun observeViewModel() {
        subscriptions += viewModel.uiModels.subscribe(::updateUi)
    }

    private fun updateUi(model: LoginContract.UiModel) {
        when (model) {
            is LoginContract.UiModel.State.Loading -> showLoadingState()
            is LoginContract.UiModel.State.Error -> showErrorState()
            is LoginContract.UiModel.State.Success -> showSuccessState()
        }
    }

    private fun showLoadingState() {
        loginButton_button.isVisible = false
        loginButton_progress.isVisible = true
        loginError.isVisible = false
    }

    private fun showErrorState() {
        loginButton_button.isVisible = true
        loginButton_progress.isVisible = false
        loginError.isVisible = true
    }

    private fun showSuccessState() {
        beginSession()
    }

    private fun observeLoginButton() {
        subscriptions += loginEnabled.subscribe(loginButton_button::setEnabled)
        subscriptions += loginButton_button.clicks().subscribe {
            viewModel.onAction(LoginContract.Action.Login(loginEmail.text.toString(),
                loginPassword.text.toString())) }
    }

    private fun observeCredentials() {
        emailObs = RxTextView.afterTextChangeEvents(loginEmail)
            .debounce(400, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
        passwordObs = RxTextView.afterTextChangeEvents(loginPassword)
            .debounce(400, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
        loginEnabled = Observables.combineLatest(emailObs, passwordObs) { _, p ->
            isValidEmail() && p.view().text.isNotEmpty() }
    }

    private fun beginSession() {
        MainActivity.navigateTo(this)
        finish()
    }

    private fun isValidEmail() =
        (!TextUtils.isEmpty(loginEmail.text)
                && Patterns.EMAIL_ADDRESS.matcher(loginEmail.text).matches())

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }
}
