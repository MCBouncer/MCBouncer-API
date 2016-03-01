package com.mcbouncer.listeners;

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

import com.mcbouncer.Config;
import com.mcbouncer.Perm;
import com.mcbouncer.Util;
import com.mcbouncer.api.MCBouncerImplementation;
import com.mcbouncer.api.MCBouncerPlayerLoginEvent;
import com.mcbouncer.exceptions.APIException;
import com.mcbouncer.models.LoginResult;

import java.util.HashMap;
import java.util.Map;

public class PlayerListener {

    private MCBouncerImplementation impl;

    public PlayerListener(MCBouncerImplementation impl) {
        this.impl = impl;
    }

    public void onPlayerLogin(MCBouncerPlayerLoginEvent event) {

        try {
            LoginResult result = this.impl.getMCBouncerPlugin().login(event.getName(), event.getUUID(), event.getAddress());
            if (result.isBanned()) {
                StringBuilder banMessage = new StringBuilder("Banned: ");
                banMessage.append(result.getBan().getReason());
                banMessage.append(".");
                if (result.getBan().getExpiry() != null) {
                    banMessage.append(" Expires in: ").append(Util.secondsToString(result.getBan().getExpiry()));
                }
                event.disallow(MCBouncerPlayerLoginEvent.Reason.KICK_BANNED, "Banned: " + banMessage.toString());
                return;
            }

            if (result.getBanCount() > this.impl.getMCBouncerPlugin().getConfig().getInt(Config.NUM_BANS_DISALLOW)) {
                event.disallow(MCBouncerPlayerLoginEvent.Reason.KICK_BANNED, this.impl.getMCBouncerPlugin().getConfig().getString(Config.MESSAGE_NUM_BANS_DISALLOW));
                return;
            }

            if (result.getNoteCount() > 0  || result.getBanCount() > 0) {
                Map<String, String> messageParams = new HashMap<>();
                messageParams.put("username", event.getName());
                messageParams.put("num_bans", String.valueOf(result.getBanCount()));
                messageParams.put("num_notes", String.valueOf(result.getNoteCount()));

                Util.broadcastMessage(impl, Perm.MESSAGE_LOGIN_NOTICE, Config.MESSAGE_LOGIN_NOTICE, messageParams);
            }
        } catch (APIException e) {
            if (!impl.getMCBouncerPlugin().getConfig().getBoolean(Config.ALLOW_ON_FAIL)) {
                event.disallow(MCBouncerPlayerLoginEvent.Reason.KICK_OTHER, impl.getMCBouncerPlugin().getConfig().getString(Config.MESSAGE_COMMUNICATION_FAILURE));
            }
        }
    }
}
