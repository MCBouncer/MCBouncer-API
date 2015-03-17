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

import java.util.Date;
import com.mcbouncer.Util;

public class UserBan {

    private String userId;
    private String username;
    private String bannedAs;
    private String reason;
    private Date timeBanned;
    private Date expiry;
    private String issuerUsername;
    private String issuerId;
    private String server;

    public UserBan(JSONObject obj) {
        userId = obj.getString("user_id");
        username = obj.getString("username");
        bannedAs = obj.getString("banned_as");
        reason = obj.getString("reason");
        timeBanned = Util.parseDate(obj.getString("time_banned"));
        expiry = Util.parseDate(obj.getString("expiry"));
        issuerUsername = obj.getString("issuer_username");
        issuerId = obj.getString("issuer_id");
        server = obj.getString("server");
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getReason() {
        return reason;
    }

    public Date getExpiry() {
        return expiry;
    }

    public String getServer() {
        return server;
    }

    public String getBannedAs() {
        return bannedAs;
    }

    public Date getTimeBanned() {
        return timeBanned;
    }

    public String getIssuerUsername() {
        return issuerUsername;
    }

    public String getIssuerId() {
        return issuerId;
    }
}
