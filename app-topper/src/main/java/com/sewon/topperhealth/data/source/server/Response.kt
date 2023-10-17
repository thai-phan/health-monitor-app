package com.sewon.topperhealth.data.source.server

import com.google.gson.annotations.SerializedName

data class ServerResponse(
    @field:SerializedName("count") val count: Int,
    @field:SerializedName("totalCount") val totalCount: Int,
    @field:SerializedName("page") val page: Int,
    @field:SerializedName("totalPages") val totalPages: Int,
    @field:SerializedName("lastItemIndex") val lastItemIndex: Int,
)
