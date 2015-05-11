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

import com.mcbouncer.*;
import com.mcbouncer.api.MCBouncerCommandSender;
import com.mcbouncer.api.MCBouncerCommand;
import com.mcbouncer.api.MCBouncerImplementation;
import com.mcbouncer.exceptions.APIException;

public class UnbanCommand extends MCBouncerCommand {

    private MCBouncerImplementation impl;

    public UnbanCommand(MCBouncerImplementation impl) {
        super("unban", Perm.COMMAND_UNBAN, "delban");
        this.impl = impl;
    }

    @Override
    public boolean onCommand(MCBouncerCommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }
        String user = args[0];

        try {
            this.impl.getMCBouncerPlugin().removeBan(user);
            sender.sendMessage(String.format("%s successfully unbanned.", user));
        } catch (final APIException e) {
            sender.sendMessage(String.format("Failed to unban %s. %s", user, e.getMessage()));
        }
        return true;
    }
}
