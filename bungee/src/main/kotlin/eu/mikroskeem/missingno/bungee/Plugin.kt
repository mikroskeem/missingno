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

package eu.mikroskeem.missingno.bungee

import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin

/**
 * @author Mark Vainomaa
 */

/**
 * Registers a listener. Note that supplied [Listener] must have no-args constructor
 *
 * @param T Class implementing [Listener]
 */
inline fun <reified T: Listener> Plugin.registerListener() {
    val listener = T::class.java.getConstructor().newInstance()
    proxy.pluginManager.registerListener(this, listener)
}

/**
 * Registers a command. Note that supplied [Command] must have no-args constructor.
 *
 * @param T Class implementing [Command]
 */
inline fun <reified T: Command> Plugin.registerCommand() {
    val command = T::class.java.getConstructor().newInstance()
    proxy.pluginManager.registerCommand(this, command)
}

/**
 * Gets [Plugin] JAR path
 */
val Plugin.path get() = this.file.toPath()