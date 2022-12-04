package edu.nitt.delta.orientation22.models

open class ClientCredentials (val param : String) {
    object ClientID : ClientCredentials("U7f5kKtVq5WOIcXu")
    object RedirectUri : ClientCredentials("https://utopia.nitt.edu/OT22/api/auth/callback")
    object State : ClientCredentials("state")
    object Nonce : ClientCredentials("123456")
}