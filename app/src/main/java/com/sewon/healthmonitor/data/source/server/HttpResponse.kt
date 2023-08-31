package com.sewon.healthmonitor.data.source.server

import com.google.gson.annotations.SerializedName

data class HttpResponse(
    @field:SerializedName("data") val data: String,
)
