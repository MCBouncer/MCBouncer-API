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
import com.mcbouncer.api.MCBouncerPlayer;
import com.mcbouncer.exceptions.APIException;
import com.mcbouncer.models.LookupResult;
import com.mcbouncer.models.UserBan;

public class LookupCommand extends MCBouncerCommand {

    private MCBouncerImplementation impl;

    public LookupCommand(MCBouncerImplementation impl) {
        super("lookup", Perm.COMMAND_LOOKUP);
        this.impl = impl;
    }

    @Override
    public boolean onCommand(MCBouncerCommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }
        String username = args[0];

        MCBouncerPlayer user = this.impl.getOfflinePlayer(username);

        try {
            LookupResult result = this.impl.getMCBouncerPlugin().lookup(user);

            sender.sendMessage(String.format("%d bans, %d notes", result.getBanCount(), result.getNoteCount()));
            for (UserBan ban : result.getBans()) {
                sender.sendMessage(String.format("%s by %s", ban.getReason(), ban.getIssuerUsername()));
            }
        } catch (APIException e) {
            e.printStackTrace();
        }

        //int banCount = plugin.getBanCount(user);
        //int noteCount = plugin.getNoteCount(user);

        return true;
    }
}

