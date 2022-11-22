package edu.nitt.delta.orientation22.models

open class ClientCredentials (val param : String) {
    object ClientID : ClientCredentials("U7f5kKtVq5WOIcXu")
    object RedirectUri : ClientCredentials("http://deltaforce.club/api/auth/callback")
    object State : ClientCredentials("state")
    object Nonce : ClientCredentials("123456")
}