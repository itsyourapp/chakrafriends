package app.itsyour.chakra.android.app.network.models

interface Environment {
    val loginBase: String
}

object StagingEnv : Environment {
    override val loginBase = "http://itsyour-staging.app/a/chakra/v1/login/"
}

object ProductionEnv : Environment {
    override val loginBase = "http://itsyour.app/a/chakra/v1/login/"
}
