package edu.nitt.delta.orientation22.models

open class ClientCredentials (val param : String) {
    object ClientID : ClientCredentials("")
    object RedirectUri : ClientCredentials("https://www.google.com/")
    object State : ClientCredentials("")
    object Nonce : ClientCredentials("")
    object ClientSecret : ClientCredentials("")
}