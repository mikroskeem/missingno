/*
 * This file is part of project Missingno, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2019 Mark Vainomaa <mikroskeem@mikroskeem.eu>
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package eu.mikroskeem.missingno.uninterruptibles

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * @author Mark Vainomaa
 */
/**
 * Executes [block] until it returns and interrupts current thread when done, if [block] threw [InterruptedException]
 */
inline fun <T> doUninterruptibly(block: () -> T): T {
    var interrupted = false
    var retVal: T
    while (true) {
        try {
            retVal = block()
            break
        } catch (e: InterruptedException) {
            interrupted = true
        }
    }
    if (interrupted) {
        Thread.currentThread().interrupt()
    }

    return retVal
}

/**
 * Awaits [ExecutorService] termination uninterruptibly
 */
fun ExecutorService.awaitTerminationUninterruptibly(timeout: Long, unit: TimeUnit): Boolean = doUninterruptibly {
    this.awaitTermination(timeout, unit)
}

/**
 * Gets [Future] value [T] uninterruptibly
 */
fun <T> Future<T>.getUninterruptibly(): T = doUninterruptibly {
    this.get()
}

/**
 * Gets [Future] value [T] uninterruptibly
 */
fun <T> Future<T>.getUninterruptibly(timeout: Long, unit: TimeUnit): T = doUninterruptibly {
    this.get(timeout, unit)
}