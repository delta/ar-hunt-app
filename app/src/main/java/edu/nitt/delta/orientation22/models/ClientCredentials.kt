package edu.nitt.delta.orientation22.models

open class ClientCredentials (val param : String) {
    object ClientID : ClientCredentials("")
    object RedirectUri : ClientCredentials("")
    object State : ClientCredentials("")
    object Nonce : ClientCredentials("")
}