package edu.nitt.delta.orientation22.models

open class ClientCredentials (val param : String) {
    object ClientID : ClientCredentials("m3yrmTh1gfRhRptR")
    object RedirectUri : ClientCredentials("http://localhost:8000/api/auth/callback")
    object State : ClientCredentials("state")
    object Nonce : ClientCredentials("123456")
}