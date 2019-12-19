package app.itsyour.chakra.android.feature.login.models

import com.google.gson.annotations.Expose

data class LoginResponse(
    @Expose var accessToken: String)