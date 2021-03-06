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

package com.mcbouncer.api;

import com.mcbouncer.MCBouncer;

import java.io.File;
import java.util.logging.Logger;

public interface MCBouncerImplementation {

    public void shutdown();

    public Logger getLogger();

    public File getDataFolder();

    public MCBouncerPlayer getOfflinePlayer(String name);

    public MCBouncerPlayer[] getOnlinePlayers();

    public String getVersion();

    public void broadcast(final String permission, final String message);

    public void broadcast(final String message);

    public MCBouncer getMCBouncerPlugin();
}
