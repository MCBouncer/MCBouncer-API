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

import com.mcbouncer.api.MCBouncerCommandSender;
import com.mcbouncer.api.MCBouncerImplementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Util {
    
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    static SimpleDateFormat dateFormatWithTZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public static void messageSender(MCBouncerImplementation impl, MCBouncerCommandSender sender, Config config, Map<String, String> params) {
        // TODO: Format the string
        String message = buildMessage(impl.getMCBouncerPlugin().getConfig().getString(config), params);
        sender.sendMessage(message);
    }

    public static String buildMessage(String inputMessage, Map<String, String> params) {
        String message = inputMessage;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            message = message.replace("{{" + key + "}}", value);
        }

        //message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    public static Date parseDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        Date d = null;
        try {
            d = dateFormatWithTZ.parse(dateStr);
        } catch (ParseException e) {
            try {
                d = dateFormat.parse(dateStr);
            }
            catch (ParseException e2) {}
        }

        return d;
    }

    public static String join(String[] strings, String separator, int index) {
        StringBuilder s = new StringBuilder("");
        for (int i = index; i < strings.length; i++) {
            s.append(strings[i]);
            if (i <= strings.length - 1) {
                s.append(separator);
            }
        }

        return s.toString();
    }
}
