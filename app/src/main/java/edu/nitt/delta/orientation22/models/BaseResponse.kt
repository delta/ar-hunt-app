package edu.nitt.delta.orientation22.models

import com.google.gson.annotations.SerializedName

open class BaseResponse<T> {
    @SerializedName("message")
    var message: T? = null

    override fun equals(response: Any?): Boolean {
        require(response is BaseResponse<*>)
        return this.message == response.message
    }
}
