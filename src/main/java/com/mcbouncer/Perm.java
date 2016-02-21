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

public enum Perm {

    /* Commands */
    COMMAND_BAN,
    COMMAND_KICK,
    COMMAND_NOTE,
    COMMAND_GLOBALNOTE,
    COMMAND_LOOKUP,
    COMMAND_RELOAD,
    COMMAND_UNBAN,
    COMMAND_REMOVE_NOTE,

    /* Messaging */
    MESSAGE_BAN,
    MESSAGE_KICK,
    MESSAGE_RELOAD,
    MESSAGE_UNBAN,
    MESSAGE_ADD_NOTE;

    private final String perm;

    private Perm() {
        this.perm = "mcbouncer." + this.name().toLowerCase().replace("_", ".");
    }

    @Override
    public String toString() {
        return this.perm;
    }
}
