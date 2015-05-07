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

package com.mcbouncer.api;

import com.mcbouncer.Perm;

public abstract class MCBouncerCommand {

    private final Perm permission;
    private final String commandName;
    private final String[] aliases;

    public MCBouncerCommand(String commandName, Perm perm, String... aliases) {
        this.permission = perm;
        this.commandName = commandName;
        this.aliases = aliases;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public Perm getPermission() {
        return this.permission;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public boolean processCommand(MCBouncerCommandSender sender, String[] args) {
        if ((this.permission != null) && (!sender.hasPermission(this.permission))) {
            // TODO: Send error to player
            return true;
        }

        return this.onCommand(sender, args);

    }

    public abstract boolean onCommand(MCBouncerCommandSender sender, String[] args);
}
