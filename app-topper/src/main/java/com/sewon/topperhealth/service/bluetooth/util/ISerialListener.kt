package com.sewon.topperhealth.service.bluetooth.util

import java.util.ArrayDeque

interface ISerialListener {
  fun onSerialConnect()

  fun onSerialConnectError(e: Exception)

  fun onSerialRead(data: ByteArray) // socket -> service

  fun onSerialRead(datas: ArrayDeque<ByteArray>) // service -> UI thread

  fun onSerialIoError(e: Exception)
}