package com.sewon.topperhealth.api.openai

import com.google.gson.annotations.SerializedName


class ResBodySleepAdvice(
  @field:SerializedName("id") val count: String,
  @field:SerializedName("object") val resObject: String,
  @field:SerializedName("created") val created: Int,
  @field:SerializedName("model") val model: String,
  @field:SerializedName("choices") val choices: ArrayList<ResChoices>,
  @field:SerializedName("usage") val usage: ResUsage,
  @field:SerializedName("system_fingerprint") val systemFingerprint: String,
)

class ResChoices(
  @field:SerializedName("index") val index: Int,
  @field:SerializedName("message") val message: ResChoicesMessage,
  @field:SerializedName("finish_reason") val finishReason: String,
)

class ResChoicesMessage(
  @field:SerializedName("role") val role: String,
  @field:SerializedName("content") val content: String,
)

class ResUsage(
  @field:SerializedName("prompt_tokens") val promptTokens: Int,
  @field:SerializedName("completion_tokens") val completionTokens: Int,
  @field:SerializedName("total_tokens") val totalTokens: Int,
)