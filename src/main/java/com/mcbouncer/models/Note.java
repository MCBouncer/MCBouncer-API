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

import com.mcbouncer.Util;
import org.json.JSONObject;

import java.util.Date;

public class Note {

    private Integer noteId;
    private String userId;
    private String username;
    private String addedAs;
    private String note;
    private Date timeAdded;
    private String issuerUsername;
    private String issuerId;
    private String server;
    private boolean global;

    public Note(JSONObject obj) {
        noteId = obj.getInt("note_id");
        userId = obj.getString("user_id");
        username = obj.getString("username");
        addedAs = obj.getString("added_as");
        note = obj.getString("note");
        timeAdded = Util.parseDate(obj.getString("time_added"));
        issuerUsername = obj.getString("issuer_username");
        issuerId = obj.getString("issuer_id");
        server = obj.getString("server");
        global = obj.getBoolean("global");
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAddedAs() {
        return addedAs;
    }

    public String getNote() {
        return note;
    }

    public Date getTimeAdded() {
        return timeAdded;
    }

    public String getIssuerUsername() {
        return issuerUsername;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public String getServer() {
        return server;
    }

    public boolean isGlobal() {
        return global;
    }

    public Integer getNoteId() {
        return noteId;
    }
}
