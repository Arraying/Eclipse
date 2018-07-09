package net.thenova.eclipse.sql;

import net.md_5.bungee.config.Configuration;
import net.thenova.eclipse.file.Configurable;

/**
 * Copyright 2018 Arraying
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public final class SQLConfig implements Configurable {

    private String host = "localhost";
    private int port = 3306;
    private String database = "Eclipse";
    private String username = "root";
    private String password = "";

    /**
     * @return database.yml
     */
    @Override
    public String getFileName() {
        return "database.yml";
    }

    /**
     * Loads the database credentials.
     * @param configuration The configuration.
     * @return Always true.
     */
    @Override
    public boolean load(Configuration configuration) {
        host = configuration.getString("host", host);
        port = configuration.getInt("port", port);
        database = configuration.getString("database", database);
        username = configuration.getString("username", username);
        password = configuration.getString("password", password);
        return true;
    }

    /**
     * Gets the JDBC connection string.
     * @return A string.
     */
    String getConnectionString() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }

    /**
     * Gets the username.
     * @return The username.
     */
    String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     * @return The password.
     */
    String getPassword() {
        return password;
    }

}
