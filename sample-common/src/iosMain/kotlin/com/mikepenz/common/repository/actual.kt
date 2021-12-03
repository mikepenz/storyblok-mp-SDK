package com.mikepenz.common.repository

import co.touchlab.kermit.NSLogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig

actual fun getLogger(): Logger = Logger(StaticConfig(logWriterList = listOf(NSLogWriter())))