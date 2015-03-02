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

import com.mcbouncer.api.Player;

import java.util.UUID;

public class ConsolePlayer implements Player {

    UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private static ConsolePlayer ourInstance = new ConsolePlayer();

    public static ConsolePlayer getInstance() {
        return ourInstance;
    }

    private ConsolePlayer() {
    }

    public void kick(String message) {
        return;
    }

    public UUID getUniqueID() {
        return this.uuid;
    }

    public String getName() {
        return "CONSOLE";
    }

    public Boolean hasPermission(Perm permission) {
        return true;
    }

    public void sendMessage(String message) {

    }
}
