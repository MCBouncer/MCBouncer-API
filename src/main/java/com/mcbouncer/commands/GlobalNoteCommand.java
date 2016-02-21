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

import java.util.HashMap;

public class GlobalNoteCommand extends MCBouncerCommand {

    private MCBouncerImplementation impl;

    public GlobalNoteCommand(MCBouncerImplementation plugin) {
        super("addgnote", Perm.COMMAND_GLOBALNOTE);
        this.impl = plugin;
    }

    @Override
    public boolean onCommand(MCBouncerCommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }
        String user = args[0];
        String note = args.length > 1 ? Util.join(args, " ", 1) : this.impl.getMCBouncerPlugin().getConfig().getString(Config.MESSAGE_DEFAULT_BAN.toString());

        HashMap<String, String> messageParams = new HashMap<>();

        messageParams.put("username", user);

        MCBouncerPlayer p;
        if (sender instanceof MCBouncerPlayer) {
            p = (MCBouncerPlayer) sender;
        } else if (sender.getName().equalsIgnoreCase("console")) {
            p = ConsolePlayer.getInstance();
        } else {
            return false;
        }

        try {
            int note_id = impl.getMCBouncerPlugin().addNote(user, note, p, true);
            messageParams.put("note_id", String.valueOf(note_id));
            Util.messageSender(impl, sender, Config.MESSAGE_NOTE_ADD_SUCCESS, messageParams);
        }
        catch (APIException e) {
            messageParams.put("error_msg", e.getMessage());
            Util.messageSender(impl, sender, Config.MESSAGE_NOTE_ADD_FAILURE, messageParams);
        }
        return true;
    }
}
