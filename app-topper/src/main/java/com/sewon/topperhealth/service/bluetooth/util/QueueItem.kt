package com.sewon.topperhealth.service.bluetooth.util

import com.sewon.topperhealth.service.bluetooth.ClassicService
import java.util.ArrayDeque

enum class QueueType {
  Connect, ConnectError, Read, IoError
}

class QueueItem {
  var type: QueueType
  var datas: ArrayDeque<ByteArray>? = null
  var e: Exception? = null

  constructor(type: QueueType) {
    this.type = type
    if (type == QueueType.Read) init()
  }

  constructor(type: QueueType, e: Exception?) {
    this.type = type
    this.e = e
  }

  constructor(type: QueueType, datas: ArrayDeque<ByteArray>?) {
    this.type = type
    this.datas = datas
  }

  fun init() {
    datas = ArrayDeque()
  }

  fun add(data: ByteArray) {
    datas!!.add(data)
  }
}