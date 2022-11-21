package edu.nitt.delta.orientation22.models

open class ClientCredentials (val param : String) {
    object ClientID : ClientCredentials("U7f5kKtVq5WOIcXu")
    object RedirectUri : ClientCredentials("http://10.1.58.131:3000/api/auth/callback")
    object State : ClientCredentials("state")
    object Nonce : ClientCredentials("123456")
}