package net.thenova.eclipse.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
public enum SQLQuery {

    /**
     * Query to create the table of memberships.
     */
    CREATE_USERS_TABLE("CREATE TABLE IF NOT EXISTS `" + Info.MEMBERSHIPS + "`(`" +
            Info.MEMBERSHIPS_UUID + "` VARCHAR(48), `" +
            Info.MEMBERSHIPS_GROUP + "` VARCHAR(32));"
    ),

    /**
     * Query to create the table of groups.
     */
    CREATE_GROUPS_TABLE("CREATE TABLE IF NOT EXISTS `" + Info.GROUPS + "`(`" +
            Info.GROUPS_NAME + "` VARCHAR(32) PRIMARY KEY, `" +
            Info.GROUPS_DEFAULT + "` BOOLEAN);"
    ),

    /**
     * Query to create the table of inheritances.
     */
    CREATE_INHERITANCE_TABLE("CREATE TABLE IF NOT EXISTS `" + Info.INHERITANCES + "`(`" +
            Info.INHERITANCES_GROUP + "` VARCHAR(32), `" +
            Info.INHERITANCES_PARENT + "` VARCHAR(32));"
    ),

    /**
     * Query to create the table of permissions.
     */
    CREATE_PERMISSIONS_TABLE("CREATE TABLE IF NOT EXISTS `" + Info.PERMISSIONS + "`(`" +
            Info.PERMISSIONS_TYPE + "` VARCHAR(10), `" +
            Info.PERMISSIONS_HOLDER + "` VARCHAR(48), `" +
            Info.PERMISSIONS_VALUE + "` TEXT);"
    ),

    /**
     * Query to get the groups of the user.
     */
    USER_GET_GROUPS("SELECT * FROM `" + Info.MEMBERSHIPS + "` WHERE `" + Info.MEMBERSHIPS_UUID + "` = ?;"),

    /**
     * Query to add a group to a user.
     */
    USER_ADD_GROUP("INSERT INTO `" + Info.MEMBERSHIPS + "` VALUES(?, ?);"),

    /**
     * Query to remove a group from a user.
     */
    USER_DELETE_GROUP("DELETE FROM `" + Info.MEMBERSHIPS + "` WHERE `" + Info.MEMBERSHIPS_UUID + "` = ? AND `" + Info.MEMBERSHIPS_GROUP + "` = ?;"),

    /**
     * Query that gets all groups.
     */
    GROUPS_GET("SELECT * FROM `" + Info.GROUPS + "`;"),

    /**
     * Query that creates a new group.
     */
    GROUP_CREATE("INSERT INTO `" + Info.GROUPS + "` VALUES(?, ?);"),

    /**
     * Query that removes a group.
     */
    GROUP_DELETE("DELETE FROM `" + Info.GROUPS + "` WHERE `" + Info.GROUPS_NAME + "` = ?;"),

    /**
     * Query that marks a group as default or not.
     */
    GROUP_DEFAULT("UPDATE `" + Info.GROUPS + "` SET `" + Info.GROUPS_DEFAULT + "` = ? WHERE `" + Info.GROUPS_NAME + "` = ?;"),

    /**
     * Query that gets all parent groups of a group.
     */
    GROUP_GET_PARENTS("SELECT * FROM `" + Info.INHERITANCES + "` WHERE `" + Info.INHERITANCES_GROUP + "` = ?;"),

    /**
     * Query that adds a parent to a group.
     */
    GROUP_ADD_PARENT("INSERT INTO `" + Info.INHERITANCES + "` VALUES (?, ?);"),

    /**
     * Query that removes a parent from a group.
     */
    GROUP_DELETE_PARENT("DELETE FROM `" + Info.INHERITANCES + "` WHERE `" + Info.INHERITANCES_GROUP + "` = ? AND `" + Info.INHERITANCES_PARENT + "` = ?;"),

    /**
     * Query that gets all permissions.
     */
    PERMISSIONS_GET("SELECT * FROM `" + Info.PERMISSIONS + "` WHERE `" + Info.PERMISSIONS_TYPE + "` = ? AND `" + Info.PERMISSIONS_HOLDER + "` = ?;"),

    /**
     * Query to add a permission.
     */
    PERMISSIONS_ADD("INSERT INTO `" + Info.PERMISSIONS + "` VALUES (?, ?, ?);"),

    /**
     * Query to remove a permission.
     */
    PERMISSIONS_DELETE("DELETE FROM `" + Info.PERMISSIONS + "` WHERE `" +
            Info.PERMISSIONS_TYPE + "` = ? AND `" +
            Info.PERMISSIONS_HOLDER + "` = ? AND `" +
            Info.PERMISSIONS_VALUE + "` = ?;"
    );

    private final String query;

    /**
     * Creates a new SQL query.
     * @param query The SQL query as a string.
     */
    SQLQuery(String query) {
        this.query = query;
    }

    /**
     * Executes the query, without a result set.
     * @param arguments Arguments.
     * @return True if successful, false otherwise.
     */
    public boolean execute(Object... arguments) {
        try {
            PreparedStatement statement = prepare((Object[]) arguments);
            statement.execute();
            statement.getConnection().close();
            return true;
        } catch(SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Executes a query and returns a result set.
     * It is important that the connection gets closed manually.
     * @param arguments Arguments.
     * @return A result set if successful, null otherwise.
     */
    public ResultSet result(Object... arguments) {
        try {
            PreparedStatement statement = prepare((Object[]) arguments);
            return statement.executeQuery();
        } catch(SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Prepares a statement.
     * @param arguments The arguments.
     * @return A statement.
     * @throws SQLException If an SQL error occurred.
     */
    private PreparedStatement prepare(Object... arguments)
            throws SQLException {
        Connection connection = SQLHandler.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        for(int i = 0; i < arguments.length; i++) {
            statement.setObject(i + 1, arguments[i]);
        }
        return statement;
    }

    @SuppressWarnings("WeakerAccess")
    public static final class Info {

        public static final String MEMBERSHIPS = "ecl_memberships";
        public static final String MEMBERSHIPS_UUID = "uuid";
        public static final String MEMBERSHIPS_GROUP = "group";
        public static final String GROUPS = "ecl_groups";
        public static final String GROUPS_NAME = "name";
        public static final String GROUPS_DEFAULT = "default";
        public static final String INHERITANCES = "ecl_inheritances";
        public static final String INHERITANCES_GROUP = "group";
        public static final String INHERITANCES_PARENT = "parent";
        public static final String PERMISSIONS = "ecl_permissions";
        public static final String PERMISSIONS_TYPE = "type";
        public static final String PERMISSIONS_TYPE_USER = "user";
        public static final String PERMISSIONS_TYPE_GROUP = "group";
        public static final String PERMISSIONS_HOLDER = "holder";
        public static final String PERMISSIONS_VALUE = "value";

    }

}
