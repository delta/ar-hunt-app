package edu.nitt.delta.orientation22.models

open class ClientCredentials (val param : String) {
    object ClientID : ClientCredentials("nu7.9dItKsL0AEzQ")
    object RedirectUri : ClientCredentials("https://utopia.nitt.edu/api/auth/callback")
    object State : ClientCredentials("state")
    object Nonce : ClientCredentials("123456")
}