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
import com.mcbouncer.models.Note;
import com.mcbouncer.models.UserBan;

import java.util.HashMap;

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

        HashMap<String, String> messageParams = new HashMap<>();

        messageParams.put("username", username);

        MCBouncerPlayer user = this.impl.getOfflinePlayer(username);

        try {
            LookupResult result = this.impl.getMCBouncerPlugin().lookup(user);

            messageParams.put("num_bans", String.valueOf(result.getBanCount()));
            messageParams.put("num_notes", String.valueOf(result.getNoteCount()));
            Util.messageSender(impl, sender, Config.MESSAGE_LOOKUP_HEADER, messageParams);
            messageParams.remove("num_bans");
            messageParams.remove("num_notes");
            int id = 1;
            for (UserBan ban : result.getBans()) {
                messageParams.put("ban_id", String.valueOf(id));
                messageParams.put("server", ban.getServer());
                messageParams.put("issuer", ban.getIssuerUsername());
                messageParams.put("reason", ban.getReason());
                String date = Util.dateToString(impl, ban.getTimeBanned());
                messageParams.put("time", date);
                if (ban.getExpiry() != null) {
                    messageParams.put("expiry", Util.secondsToString(ban.getExpiry()));
                    Util.messageSender(impl, sender, Config.MESSAGE_LOOKUP_BAN_WITH_EXPIRY, messageParams);
                } else {
                    Util.messageSender(impl, sender, Config.MESSAGE_LOOKUP_BAN, messageParams);
                }
                id++;
            }
            messageParams.remove("ban_id");

            for (Note note : result.getNotes()) {
                messageParams.put("note_id", String.valueOf(note.getNoteId()));
                messageParams.put("server", note.getServer());
                messageParams.put("issuer", note.getIssuerUsername());
                messageParams.put("note", note.getNote());
                String date = Util.dateToString(impl, note.getTimeAdded());
                messageParams.put("time", date);
                Util.messageSender(impl, sender, Config.MESSAGE_LOOKUP_NOTE, messageParams);
            }
        } catch (APIException e) {
            e.printStackTrace();
        }

        //int banCount = plugin.getBanCount(user);
        //int noteCount = plugin.getNoteCount(user);

        return true;
    }
}

