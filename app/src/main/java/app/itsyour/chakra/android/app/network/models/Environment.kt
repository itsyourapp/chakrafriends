package app.itsyour.chakra.android.app.network.models

interface Environment {
    val baseUrl: String
}

object StagingEnv : Environment {
    override val baseUrl = "http://10.0.2.2:3001/api/v1/"
}

object ProductionEnv : Environment {
    override val baseUrl = "localhost:3001/api/v1/"
}
