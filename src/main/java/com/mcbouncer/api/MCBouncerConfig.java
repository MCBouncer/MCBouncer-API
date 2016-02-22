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

import com.mcbouncer.Config;
import com.mcbouncer.api.MCBouncerImplementation;
import com.mcbouncer.exceptions.MCBouncerException;

import java.io.File;
import java.io.IOException;

public abstract class MCBouncerConfig {

    protected MCBouncerImplementation impl;
    protected File file;

    public MCBouncerConfig(MCBouncerImplementation impl) {
        this.impl = impl;
    }

    public abstract void load() throws MCBouncerException;
    public abstract void load(String filename) throws MCBouncerException;

    public abstract void save() throws IOException;

    public Integer getInt(Config config) {
        return getInt(config.toString(), (Integer)config.getDefault());
    }
    public abstract Integer getInt(String path);
    public abstract Integer getInt(String path, Integer def);

    public String getString(Config config) {
        return getString(config.toString(), (String)config.getDefault());
    }
    public abstract String getString(String path);
    public abstract String getString(String path, String def);


    public Boolean getBoolean(Config config) {
        return getBoolean(config.toString(), (Boolean)config.getDefault());
    }
    public abstract Boolean getBoolean(String path);
    public abstract Boolean getBoolean(String path, Boolean def);

    public Double getDouble(Config config) {
        return getDouble(config.toString(), (Double)config.getDefault());
    }
    public abstract Double getDouble(String path);
    public abstract Double getDouble(String path, Double def);


    public abstract void putInt(String path, Integer i);
    public abstract void putString(String path, String s);
    public abstract void putBoolean(String path, Boolean b);
    public abstract void putDouble(String path, Double d);
}
