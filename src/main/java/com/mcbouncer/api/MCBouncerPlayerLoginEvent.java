package com.mcbouncer.api;
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

import java.net.InetAddress;
import java.util.UUID;

public class MCBouncerPlayerLoginEvent {
    public enum Reason {
        ALLOWED, KICK_BANNED, KICK_FULL, KICK_OTHER, KICK_WHITELIST,
    }

    private String name;
    private String disallowMessage;
    private UUID uuid;
    private Reason reason;
    private InetAddress ipAddress;


    private boolean disallowed = false;

    public MCBouncerPlayerLoginEvent(String name, InetAddress ipAddress, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.ipAddress = ipAddress;
        this.disallowMessage = null;
    }

    public Reason getReason() {
        return this.reason;
    }

    public String getKickMessage() {
        return this.disallowMessage;
    }

    public void disallow(Reason reason, String message) {
        this.reason = reason;
        this.disallowMessage = message;
        this.disallowed = true;
    }

    public void allow() {
        this.reason = Reason.ALLOWED;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public InetAddress getAddress() {
        return this.ipAddress;
    }

    public boolean isDisallowed() {
        return this.disallowed;
    }
}
