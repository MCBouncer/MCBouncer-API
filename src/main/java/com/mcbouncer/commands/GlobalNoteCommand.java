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
import com.mcbouncer.api.CommandSender;
import com.mcbouncer.api.MCBouncerCommand;
import com.mcbouncer.api.Player;
import com.mcbouncer.exceptions.APIException;

public class GlobalNoteCommand extends MCBouncerCommand {

    private MCBouncer plugin;

    public GlobalNoteCommand(MCBouncer plugin) {
        super("addgnote", Perm.COMMAND_GLOBALNOTE);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }
        String user = args[0];
        String note = args.length > 1 ? Util.join(args, " ", 1) : this.plugin.getConfig().getString(Config.MESSAGE_DEFAULT_BAN.toString());

        Player p;
        if (sender instanceof Player) {
            p = (Player) sender;
        } else if (sender.getName().equalsIgnoreCase("console")) {
            p = ConsolePlayer.getInstance();
        } else {
            return false;
        }

        try {
            plugin.addNote(user, note, p, true);
        }
        catch (APIException e) {
            // TODO: Add better error message.  This should pull from config.
            sender.sendMessage("ERROR!");
        }
        return true;
    }
}
