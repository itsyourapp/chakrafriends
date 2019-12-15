package app.itsyour.chakra.android.feature.login.models

import com.google.gson.annotations.Expose

data class LoginRequest (
    @Expose val email: String,
    @Expose val password: String)