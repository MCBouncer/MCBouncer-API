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

import com.mcbouncer.api.MCBouncerConfig;
import com.mcbouncer.api.MCBouncerImplementation;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlConfig extends MCBouncerConfig {

    private Map<String, Object> map = new LinkedHashMap<String, Object>();

    public YamlConfig(MCBouncerImplementation impl) {
        super(impl);
    }

    @Override
    public void load() {
        load("config.yml");
    }

    @Override
    public void load(String filename) {
        this.file = new File(this.impl.getDataFolder(), filename);

        if (!this.file.exists()) {
            this.impl.getMCBouncerPlugin().saveResource(filename, false);
        }
    }

    @Override
    public void save() throws IOException {
        Yaml yaml = new Yaml();
        OutputStreamWriter writer = new FileWriter(this.file);
        try {
            yaml.dump(map, writer);
        } finally {
            writer.close();
        }
    }

    Object get(String path) {
        try {
            return this.get(path, this.map);
        } catch (final Exception e) {
            return null;
        }
    }

    void put(String path, Object o) {
        this.put(path, o, this.map);
    }

    @Override
    public Integer getInt(String path) {
        return getInt(path, 0);
    }

    @Override
    public Integer getInt(String path, Integer def) {
        try {
            String s = this.get(path).toString();
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            return def;
        }
    }

    @Override
    public String getString(String path) {
        return getString(path, "");
    }

    @Override
    public String getString(String path, String def) {
        try {
            return this.get(path).toString();
        } catch (final Exception e) {
            return def;
        }
    }

    @Override
    public Boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    @Override
    public Boolean getBoolean(String path, Boolean def) {
        try {
            String s = this.get(path).toString();
            return Boolean.parseBoolean(s);
        } catch (final Exception e) {
            return def;
        }
    }

    @Override
    public Double getDouble(String path) {
        return this.getDouble(path, 0.0);
    }

    @Override
    public Double getDouble(String path, Double def) {
        try {
            String s = this.get("path").toString();
            return Double.parseDouble(s);
        } catch (final NumberFormatException e) {
            return def;
        }

    }

    @Override
    public void putInt(String path, Integer i) {
        this.put(path, (Object)i);
    }

    @Override
    public void putString(String path, String s) {
        this.put(path, (Object)s);
    }

    @Override
    public void putBoolean(String path, Boolean b) {
        this.put(path, (Object)b);
    }

    @Override
    public void putDouble(String path, Double d) {
        this.put(path, (Object)d);
    }

    private Object get(String string, Map<String, Object> map) {
        if (string.contains(".") && (string.indexOf(".") != (string.length() - 1))) {
            final Object o = map.get(string.substring(0, string.indexOf(".")));
            if (o instanceof Map) {
                return this.get(string.substring(string.indexOf(".") + 1), (Map<String, Object>) o);
            } else {
                return null;
            }
        }
        return map.get(string);
    }

    private void put(String string, Object object, Map<String, Object> map) {
        if (string.contains(".") && (string.indexOf(".") != (string.length() - 1))) {
            String key = string.substring(0, string.indexOf("."));
            Object o = map.get(key);
            if (o == null) {
                o = new LinkedHashMap<String, Object>();
                map.put(key, o);
            }
            if (o instanceof Map) {
                this.put(string.substring(string.indexOf(".") + 1), object, (Map<String, Object>) o);
                return;
            }
            // TODO: raise an error here because it's not a map!
            return;
        }

        map.put(string, object);
    }
}
