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

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.Locale

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
    server.pluginManager.registerEvents(listener, this)
}

/**
 * Registers a command. Note that supplied [CommandExecutor] must have no-args constructor.
 *
 * @param T Class implementing [CommandExecutor]
 */
inline fun <reified T: CommandExecutor> JavaPlugin.registerCommand(name: String) {
    val commandExecutor = T::class.java.getConstructor().newInstance()
    val command = getCommand(name)
    if(command != null) {
        command.setExecutor(commandExecutor)
        if(commandExecutor is TabCompleter)
            command.tabCompleter = commandExecutor
    } else {
        val wrappedCommand = object: Command(name) {
            private val doesTabComplete = commandExecutor is TabCompleter

            override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
                return commandExecutor.onCommand(sender, this, commandLabel, args)
            }

            override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
                if(doesTabComplete)
                    return (commandExecutor as TabCompleter).onTabComplete(sender, this, alias, args) ?: ArrayList()
                return super.tabComplete(sender, alias, args)
            }
        }
        server.commandMap.register(name.toLowerCase(Locale.ENGLISH), wrappedCommand)
    }
}