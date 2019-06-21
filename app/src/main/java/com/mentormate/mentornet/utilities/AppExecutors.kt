package com.mentormate.mentornet.utilities

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class AppExecutors constructor(
    val diskIO: Executor,
    val networkIO: Executor,
    val mainThread: Executor
) {
    @Inject
    constructor() : this(Executors.newFixedThreadPool(6), Executors.newFixedThreadPool(6), MainThreadExecutor())

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}