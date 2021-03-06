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
    static SimpleDateFormat displayDate = new SimpleDateFormat("");

    public static void messageSender(MCBouncerImplementation impl, MCBouncerCommandSender sender, Config config, Map<String, String> params) {
        String message = buildMessage(impl.getMCBouncerPlugin().getConfig().getString(config), params);
        sender.sendMessage(message);
    }

    public static void broadcastMessage(MCBouncerImplementation impl, Config config, Map<String, String> params) {
        broadcastMessage(impl, null, config, params);
    }

    public static void broadcastMessage(MCBouncerImplementation impl, Perm permission, Config config, Map<String, String> params) {
        String message = buildMessage(impl.getMCBouncerPlugin().getConfig().getString(config), params);

        if (permission == null) {
            impl.broadcast(permission.toString(), message);
        }
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

    public static String dateToString(MCBouncerImplementation impl, Date date) {
        String dateFormat = impl.getMCBouncerPlugin().getConfig().getString(Config.DATE_FORMAT);
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public static String join(String[] strings, String separator, int index) {
        StringBuilder s = new StringBuilder("");
        for (int i = index; i < strings.length; i++) {
            s.append(strings[i]);
            if (i < strings.length - 1) {
                s.append(separator);
            }
        }

        return s.toString();
    }

    private static Integer SECONDS_IN_DAY = 86400;
    private static Integer SECONDS_IN_HOUR = 3600;
    private static Integer SECONDS_IN_MINUTE = 60;

    public static String secondsToString(Integer seconds) {
        // Seconds in a day: 86400
        // Seconds in an hour: 3600
        // Seconds in a minute: 60

        StringBuilder time = new StringBuilder("");

        if (seconds > SECONDS_IN_DAY) {
            Integer amount = seconds / SECONDS_IN_DAY;
            seconds = seconds % SECONDS_IN_DAY;
            time.append(amount).append("d");
        }

        if (seconds > SECONDS_IN_HOUR) {
            Integer amount = seconds / SECONDS_IN_HOUR;
            seconds = seconds % SECONDS_IN_HOUR;
            time.append(amount).append("h");
        }

        if (seconds > SECONDS_IN_MINUTE) {
            Integer amount = seconds / SECONDS_IN_MINUTE;
            seconds = seconds % SECONDS_IN_MINUTE;
            time.append(amount).append("m");
        }

        return time.toString();
    }
}
