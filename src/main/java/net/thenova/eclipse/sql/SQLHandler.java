package net.thenova.eclipse.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;
import net.thenova.eclipse.Eclipse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public final class SQLHandler {

    private static final Object mutex = new Object();
    private static SQLHandler instance;
    private HikariPool pool;

    /**
     * Private constructor to prevent instantiation.
     */
    private SQLHandler() {}

    /**
     * Gets the instance of the SQL handler.
     * Unlike the core Eclipse singleton, this is threadsafe.
     * @return The instance.
     */
    public static SQLHandler getInstance() {
        if(instance == null) {
            synchronized(mutex) {
                if(instance == null) {
                    instance = new SQLHandler();
                }
            }
        }
        return instance;
    }

    /**
     * Static utility method to close a result set.
     * Typing the whole thing is slightly tedious every time.
     * @param resultSet The result set.
     */
    public static void close(ResultSet resultSet) {
        try {
            resultSet.getStatement().getConnection().close();
        } catch(SQLException exception) {
            Eclipse.Log.severe("Attempted to close connection; received SQL error: %s.", exception.getMessage());
        }
    }

    /**
     * Attempts to connect to the database, and prepare tables.
     * @return False if the connection failed.
     */
    public boolean prepare() {
        HikariConfig hikariConfig = new HikariConfig();
        SQLConfig sqlConfig = Eclipse.getInstance().getSqlConfig();
        hikariConfig.setPoolName("eclipse_pool");
        hikariConfig.setLeakDetectionThreshold(3000);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setIdleTimeout(20000);
        hikariConfig.setJdbcUrl(sqlConfig.getConnectionString());
        hikariConfig.setUsername(sqlConfig.getUsername());
        if(!sqlConfig.getPassword().isEmpty()) {
            hikariConfig.setPassword(sqlConfig.getPassword());
        }
        pool = new HikariPool(hikariConfig);
        return SQLQuery.CREATE_USERS_TABLE.execute()
                && SQLQuery.CREATE_GROUPS_TABLE.execute()
                && SQLQuery.CREATE_INHERITANCE_TABLE.execute()
                && SQLQuery.CREATE_PERMISSIONS_TABLE.execute();
    }

    /**
     * Shuts down the connection pool.
     */
    public void shutdown() {
        if(pool == null) {
            return;
        }
        try {
            pool.shutdown();
        } catch(InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets a connection from the pool.
     * @return The connection.
     * @throws SQLException If there is an SQL error.
     */
    Connection getConnection()
            throws SQLException {
        return pool.getConnection();
    }

}
