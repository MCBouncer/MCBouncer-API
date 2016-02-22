/*
 * MCBouncer-API
 * Copyright 2012-2014 Deaygo Jarkko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mcbouncer.commands;

import com.mcbouncer.Config;
import com.mcbouncer.Perm;
import com.mcbouncer.Util;
import com.mcbouncer.api.MCBouncerCommandSender;
import com.mcbouncer.api.MCBouncerImplementation;
import com.mcbouncer.exceptions.MCBouncerException;

import java.util.HashMap;

public class MCBouncerPluginCommand extends com.mcbouncer.api.MCBouncerCommand {

    private MCBouncerImplementation impl;

    public MCBouncerPluginCommand(MCBouncerImplementation impl) {
        super("mcbouncer", null, "mcb");
        this.impl = impl;
    }

    @Override
    public boolean onCommand(MCBouncerCommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }
        String command = args[0];

        HashMap<String, String> messageParams = new HashMap<>();

        if (command.equalsIgnoreCase("reload")) {
            if (sender.hasPermission(Perm.COMMAND_RELOAD)) {
                try {
                    this.impl.getMCBouncerPlugin().getConfig().load();
                    Util.messageSender(impl, sender, Config.MESSAGE_RELOAD_SUCCESS, messageParams);
                    messageParams.put("issuer", sender.getName());

                    Util.broadcastMessage(impl, Perm.MESSAGE_RELOAD, Config.MESSAGE_RELOAD_BROADCAST, messageParams);
                }
                catch (MCBouncerException ex) {
                    // TODO: Load error message
                    sender.sendMessage("Failed to load configuration");
                }

            } else {
                // TODO: Error message
                sender.sendMessage("You don't have permission for that.");
            }
        } else if (command.equalsIgnoreCase("help")) {
            // TODO: Help message
        } else if (command.equalsIgnoreCase("version")) {
            sender.sendMessage("MCBouncer version: " + this.impl.getVersion());
        }

        return true;
    }
}
