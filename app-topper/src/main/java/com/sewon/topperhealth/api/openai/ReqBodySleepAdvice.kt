package com.sewon.topperhealth.api.openai


class RequestBodySleepAdvice(
  val model: String,
  val messages: ArrayList<AdviceMessage>,
  val temperature: Int,
  val max_tokens: Int,
  val top_p: Int,
  val frequency_penalty: Int,
  val presence_penalty: Int,
)

class AdviceMessage(
  val role: String,
  val content: String,
)
