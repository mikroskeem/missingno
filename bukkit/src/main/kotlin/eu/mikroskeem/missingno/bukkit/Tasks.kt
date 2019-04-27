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

package eu.mikroskeem.missingno.bukkit

import org.bukkit.plugin.Plugin

/**
 * @author Mark Vainomaa
 */

/**
 * Runs a task
 *
 * @param async Whether task should be scheduled async or not
 * @param task Task to run
 */
fun Plugin.runTask(async: Boolean = false, task: () -> Unit) {
    if(async) {
        server.scheduler.runTaskAsynchronously(this, task)
    } else {
        server.scheduler.runTask(this, task)
    }
}

/**
 * Runs task later
 *
 * @param async Whether task should be scheduled async or not
 * @param delay How many ticks later should task be scheduled
 * @param task Task to run
 */
fun Plugin.runTaskLater(async: Boolean = false, delay: Long, task: () -> Unit) {
    if(delay == 0L) {
        runTask(async, task)
        return
    }
    if(async) {
        server.scheduler.runTaskLaterAsynchronously(this, task, delay)
    } else {
        server.scheduler.runTaskLater(this, task, delay)
    }
}