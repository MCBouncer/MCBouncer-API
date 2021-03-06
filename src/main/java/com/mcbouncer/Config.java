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

package com.mcbouncer;

public enum Config {

    APIKEY("apiKey", "REPLACE"),
    MESSAGE_DEFAULT_KICK("defaultKickMessage", "Kicked by an admin."),
    MESSAGE_DEFAULT_BAN("defaultKickMessage", "Banned for rule violation"),
    NUM_BANS_DISALLOW("numBansDisallow", -1),
    BROADCAST_BAN_MESSAGES("broadcast-ban-messages", false),
    BROADCAST_NOTE_MESSAGES("broadcast-note-messages", false),
    BROADCAST_KICK_MESSAGES("broadcast-kick-messages", false),
    DISABLED_IP_FUNCTIONS("disableIPFunctions", false),
    ALLOW_ON_FAIL("allow-on-fail", false),
    WEBSITE("website", "http://mcbouncer.com"),
    MESSAGE_BAN_BROADCAST("messages.ban.broadcast", "&2{{username}} was banned by {{issuer}} for {{reason}}"),
    MESSAGE_BAN_ADD_SUCCESS("messages.ban.add-success", "&2{{username}} was successfully banned."),
    MESSAGE_BAN_ADD_FAILURE("messages.ban.add-failure", "&4{{username}} was not banned: {{error_msg}}."),
    MESSAGE_BAN_DEL_SUCCESS("messages.ban.delete-success", "&2{{username}} unbanned successfully."),
    MESSAGE_BAN_DEL_FAILURE("messages.ban.delete-failure", "&4{{username}} could not be unbanned: {{error_msg}}."),
    MESSAGE_NOTE_BROADCAST("messages.note.broadcast", "&2{{issuer}} added a note to {{username}} - {{note}}"),
    MESSAGE_NOTE_ADD_SUCCESS("messages.note.add-success", "&2{{note_id}} created for {{username}}."),
    MESSAGE_NOTE_ADD_FAILURE("messages.note.add-failure", "&4Failed to add note to {{username}}: {{error_msg}}."),
    MESSAGE_NOTE_DEL_SUCCESS("messages.note.delete-success", "&2Removed note {{note_id}} successfully."),
    MESSAGE_NOTE_DEL_FAILURE("messages.note.delete-failure", "&4Failed to remove note {{note_id}}: {{error_msg}}."),
    MESSAGE_LOOKUP_HEADER("messages.lookup.header", "&3{{username}} has {{num_bans}} ban(s) and {{num_notes}} note(s)."),
    MESSAGE_LOOKUP_BAN("messages.lookup.ban", "&9Ban #{{ban_id}}: {{server}} ({{issuer}}) [{{reason}}] {{time}}."),
    MESSAGE_LOOKUP_BAN_WITH_EXPIRY("messages.lookup.ban-with-expiry", "&9Ban #{{ban_id}}: {{server}} ({{issuer}}) [{{reason}}] {{time}} {{expiry}}"),
    MESSAGE_LOOKUP_NOTE("messages.lookup.note", "&9Note #{{note_id}}: {{server}} ({{issuer}}) [{{note}}] {{time}}."),
    MESSAGE_KICK_SUCCESS("messages.kick.success", "&2{{username}} was kicked successfully"),
    MESSAGE_KICK_FAILURE("messages.kick.failure", "&4Failed to kick {{username}}: {{error_msg}}"),
    MESSAGE_KICK_BROADCAST("messages.kick.broadcast", "&2{{username}} was kicked by {{issuer}} for {{reason}}"),
    MESSAGE_RELOAD_SUCCESS("messages.reload.success", "&2Reloaded the MCBouncer configuration successfully."),
    MESSAGE_RELOAD_BROADCAST("messages.reload.broadcast", "&2{{issuer}} reloaded the MCBouncer configuration."),
    MESSAGE_COMMUNICATION_FAILURE("messages.communication-failure", "Failed to communicate with ban server."),
    MESSAGE_NUM_BANS_DISALLOW("messages.num-bans-disallow", "You are banned on too many servers to join here."),
    MESSAGE_LOGIN_NOTICE("messages.login-notice", "&2{{username}} has {{num_bans}} ban(s) and {{num_notes}} note(s)."),
    DATE_FORMAT("date-format", "MMM.d.YYYY@HH:mm:ss");

    private final String key;
    private final Object def;

    Config(String key, Object def) {
        this.key = key;
        this.def = def;
    }

    Config(Object def) {
        this.key = this.name().toLowerCase().replace("_", ".");
        this.def = def;
    }

    @Override
    public String toString() {
        return this.key;
    }

    public Object getDefault() {
        return this.def;
    }
}
