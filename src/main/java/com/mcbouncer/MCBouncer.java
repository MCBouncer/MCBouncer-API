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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mcbouncer.api.MCBouncerConfig;
import com.mcbouncer.api.MCBouncerImplementation;
import com.mcbouncer.api.Player;
import com.mcbouncer.exceptions.APIException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MCBouncer {

    private MCBouncerImplementation impl;
    private MCBouncerConfig config;

    public MCBouncer(MCBouncerImplementation impl, MCBouncerConfig config) {
        this.impl = impl;
        this.config = config;
        this.config.load();
    }

    public MCBouncerConfig getConfig() {
        return this.config;
    }

    public File getDataFolder() {
        return impl.getDataFolder();
    }

    /**
     * Get an InputStream of a file by path
     *
     * @param path Path to the file
     * @return InputStream for the file or null if not found
     * @throws IOException If something goes terribly wrong
     */
    InputStream getResource(String path) throws IOException {
        final URL url = this.getClass().getClassLoader().getResource(path);
        if (url == null) {
            return null;
        }
        final URLConnection urlConnection = url.openConnection();
        urlConnection.setUseCaches(false);
        return urlConnection.getInputStream();
    }

    public void saveResource(String path, boolean overwrite) {
        try {
            InputStream input = this.getResource(path);
            final File outputFile = new File(this.getDataFolder(), path);
            final int slashLocation = path.lastIndexOf("/");
            final File outputFolder = slashLocation >= 0 ? new File(this.getDataFolder(), path.substring(0, slashLocation)) : this.getDataFolder();
            outputFolder.mkdirs();
            if (overwrite || !outputFile.exists()) {
                try {
                    OutputStream output = new FileOutputStream(outputFile);
                    final byte[] buf = new byte[1024];
                    int len;
                    while ((len = input.read(buf)) > 0) {
                        output.write(buf, 0, len);
                    }
                }
                catch (IOException e) {

                }
            }
        } catch (IOException e) {
            // TODO complain
        }
    }

    public Player getPlayer(String username) {
        if (username.equalsIgnoreCase("console")) {
            return ConsolePlayer.getInstance();
        }
        return this.impl.getPlayer(username);
    }

    private JSONObject delete(final String resource, final String object) throws APIException{
        return delete(resource, object, null);
    }

    private JSONObject delete(final String resource, final String object, Map<String, String> query) throws APIException {
        String url = String.format("%s/api/v2/%s/%s", getConfig().getString(Config.WEBSITE), resource, object);
        HttpRequestWithBody req = Unirest.delete(url);
        req.header("Authorization", "APIKey " + getConfig().getString(Config.APIKEY));

        if (query != null) {
            for (Map.Entry<String, String> entry : query.entrySet()) {
                req.queryString(entry.getKey(), entry.getValue());
            }
        }

        try {
            HttpResponse<JsonNode> response = req.asJson();
            JSONObject obj = response.getBody().getObject();
            if (response.getStatus() >= 500) {
                throw new APIException("Server error");
            }
            if (response.getStatus() >= 400 && response.getStatus() < 500) {
                throw new APIException(obj.getString("message"));
            }
            return obj;
        } catch (final UnirestException e) {
            throw new APIException(String.format("Failed to delete %s %s", resource, object), e);
        }
    }

    public void addBan(final String username, final String reason, final String issuerName) {
        Player user = this.getPlayer(username);
        Player issuer = this.getPlayer(issuerName);
        addBan(user, reason, issuer);
    }

    public void addBan(final String username, final String reason, final Player issuer) {
        Player user = this.getPlayer(username);
        addBan(user, reason, issuer);
    }

    public void addBan(Player user, final String reason, final Player issuer) {
        // TODO: Add ban here
    }

    public void removeBan(final String username) throws APIException {
        Player user = this.getPlayer(username);
        removeBan(user);
    }

    public void removeBan(final Player user) throws APIException {
        Map<String, String> query = new HashMap<String, String>();
        query.put("user_id", "");
        delete("ban", user.getUniqueID().toString());
    }

    public void addNote(final String username, final String note, final String issuerName, boolean global) {
        Player user = this.getPlayer(username);
        Player issuer = this.getPlayer(issuerName);
        addNote(user, note, issuer, global);
    }

    public void addNote(final String username, final String note, final Player issuer, boolean global) {
        Player user = this.getPlayer(username);
        addNote(user, note, issuer, global);
    }

    public void addNote(Player user, final String note, final Player issuer, boolean global) {
        // TODO: Add note here
    }
}
