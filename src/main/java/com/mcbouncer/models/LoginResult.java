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

package com.mcbouncer.models;

import org.json.JSONObject;

public class LoginResult {

    private boolean banned;
    private int banCount;
    private int noteCount;
    private int ipBanCount;
    private UserBan ban;

    public LoginResult(JSONObject result) {
        banned = result.has("ban");
        banCount = result.getInt("ban_count");
        ipBanCount = result.getInt("ipban_count");
        noteCount = result.getInt("note_count");
        if (banned) {
            ban = new UserBan(result.getJSONObject("ban"));
        }
    }

    public int getBanCount() {
        return banCount;
    }

    public int getNoteCount() {
        return noteCount;
    }

    public int getIpBanCount() {
        return ipBanCount;
    }

    public boolean isBanned() {
        return banned;
    }

    public UserBan getBan() {
        return ban;
    }

}
