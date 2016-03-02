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
import com.mcbouncer.exceptions.MCBouncerException;

import java.util.HashMap;

public class BanCommand extends MCBouncerCommand {

    private MCBouncerImplementation impl;

    public BanCommand(MCBouncerImplementation plugin) {
        super("ban", Perm.COMMAND_BAN, "addban");
        this.impl = plugin;
    }

    @Override
    public boolean onCommand(MCBouncerCommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }
        HashMap<String, String> messageParams = new HashMap<>();


        String user = args[0];
        String reason = args.length > 1 ? Util.join(args, " ", 1) : this.impl.getMCBouncerPlugin().getConfig().getString(Config.MESSAGE_DEFAULT_BAN.toString());

        messageParams.put("username", user);

        MCBouncerPlayer s;
        if (sender instanceof MCBouncerPlayer) {
            s = (MCBouncerPlayer) sender;
        } else if (sender.getName().equalsIgnoreCase("console")) {
            s = ConsolePlayer.getInstance();
        } else {
            return false;
        }

        try {
            MCBouncerPlayer p = impl.getOfflinePlayer(user);
            impl.getMCBouncerPlugin().addBan(p, reason, s);
            Util.messageSender(impl, sender, Config.MESSAGE_BAN_ADD_SUCCESS, messageParams);

            boolean broadcastMessage = this.impl.getMCBouncerPlugin().getConfig().getBoolean(Config.BROADCAST_BAN_MESSAGES);
            messageParams.put("issuer", s.getName());
            messageParams.put("reason", reason);

            Perm perm = null;
            if (!broadcastMessage) {
                perm = Perm.MESSAGE_BAN;
            }

            Util.broadcastMessage(impl, perm, Config.MESSAGE_BAN_BROADCAST, messageParams);

            if (p.isOnline()) {
                p.kick("Banned: " + reason);
            }
        }
        catch (APIException e) {
            messageParams.put("error_msg", e.getMessage());
            Util.messageSender(impl, sender, Config.MESSAGE_BAN_ADD_FAILURE, messageParams);
        }
        catch (MCBouncerException e) {
            messageParams.put("error_msg", e.getMessage());
            Util.messageSender(impl, sender, Config.MESSAGE_BAN_ADD_FAILURE, messageParams);
        }
        return true;
    }
}
