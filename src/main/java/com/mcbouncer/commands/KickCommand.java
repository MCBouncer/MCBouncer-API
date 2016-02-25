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

import java.util.HashMap;

public class KickCommand extends MCBouncerCommand {

    private MCBouncerImplementation impl;

    public KickCommand(MCBouncerImplementation impl) {
        super("kick", Perm.COMMAND_KICK, "boot");
        this.impl = impl;
    }

    @Override
    public boolean onCommand(MCBouncerCommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }
        String user = args[0];
        String reason = args.length > 1 ? Util.join(args, " ", 1) : this.impl.getMCBouncerPlugin().getConfig().getString(Config.MESSAGE_DEFAULT_KICK.toString());

        HashMap<String, String> messageParams = new HashMap<>();

        messageParams.put("username", user);

        MCBouncerPlayer p = this.impl.getOfflinePlayer(user);

        if (p.isOnline()) {
            p.kick(reason);
            Util.messageSender(impl, sender, Config.MESSAGE_KICK_SUCCESS, messageParams);

            boolean broadcastMessage = impl.getMCBouncerPlugin().getConfig().getBoolean(Config.BROADCAST_KICK_MESSAGES);
            messageParams.put("issuer", sender.getName());
            messageParams.put("reason", reason);

            Perm perm = null;
            if (!broadcastMessage) {
                perm = Perm.MESSAGE_KICK;
            }

            Util.broadcastMessage(impl, perm, Config.MESSAGE_KICK_BROADCAST, messageParams);

        } else {
            messageParams.put("error_msg", "Player not online");
            Util.messageSender(impl, sender, Config.MESSAGE_KICK_FAILURE, messageParams);
        }
        return true;
    }
}
