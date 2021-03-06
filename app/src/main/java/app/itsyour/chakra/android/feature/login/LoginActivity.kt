package app.itsyour.chakra.android.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import app.itsyour.chakra.android.R
import app.itsyour.chakra.android.feature.main.MainActivity
import app.itsyour.chakra.android.view.LoadingDialogFragment
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

    companion object {
        fun navigateTo(context: Context)
            = context.startActivity(Intent(context, LoginActivity::class.java)
                .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK })
    }

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
            is LoginContract.UiModel.State.Ready -> showReadyState()
            is LoginContract.UiModel.State.Loading -> showLoadingState()
            is LoginContract.UiModel.State.Error -> showErrorState()
            is LoginContract.UiModel.State.Success -> showSuccessState()
        }
    }

    private fun showReadyState() {
        hideBottomSheet()
        loginError.visibility = View.GONE
    }

    private fun showLoadingState() {
        showBottomSheet()
        loginError.visibility = View.GONE
    }

    private fun showErrorState() {
        hideBottomSheet()
        loginError.visibility = View.VISIBLE
    }

    private fun showBottomSheet() {
        LoadingDialogFragment.newInstance()
            .show(supportFragmentManager, LoadingDialogFragment.TAG)
    }

    private fun hideBottomSheet() {
        (supportFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG)
                as? LoadingDialogFragment)?.dismiss()
    }

    private fun showSuccessState() {
        beginSession()
    }

    private fun observeLoginButton() {
        subscriptions += loginEnabled.subscribe(loginButton::setEnabled)
        subscriptions += loginButton.clicks().subscribe {
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
