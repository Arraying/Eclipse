package net.thenova.eclipse.permission;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.sql.SQLHandler;
import net.thenova.eclipse.sql.SQLQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

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
public abstract class AbstractPermissible {

    private final String type;
    private final String identifier;
    protected final Set<String> basePermissions = new HashSet<>();

    /**
     * Creates a new loadable with the specified type and identifier.
     * @param type The type.
     * @param identifier The identifier.
     */
    protected AbstractPermissible(String type, String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    /**
     * Checks whether the permissible has permission.
     * This does not go through the base permission, the implementation must manually handle this.
     * @param permission The permission node.
     * @return True if it does, false otherwise.
     */
    public abstract boolean hasPermission(String permission);

    /**
     * Loads more required data.
     */
    protected abstract void loadMore();

    /**
     * Loads the data.
     */
    protected final void load() {
        try {
            ResultSet resultSet = SQLQuery.PERMISSIONS_GET.result(type, identifier);
            if(resultSet == null) {
                Eclipse.Log.severe("Could not load permissions for the entity (type: %s) \"%s\".", type, identifier);
                return;
            }
            while(resultSet.next()) {
                basePermissions.add(resultSet.getString(SQLQuery.Info.PERMISSIONS_VALUE));
            }
            loadMore();
            SQLHandler.close(resultSet);
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Adds a permission to the permissible.
     * @param permission The permission.
     */
    public void addPermission(String permission) {
        if(basePermissions.contains(permission)) {
           return;
        }
        if(!SQLQuery.PERMISSIONS_ADD.execute(type, identifier, permission)) {
            Eclipse.Log.severe("Could not add a permission to the entity (type: %s) \"%s\".", type, identifier);
            return;
        }
        basePermissions.add(permission);
    }

    /**
     * Removes a permission from the permissible.
     * @param permission The permission.
     */
    public void removePermission(String permission) {
        if(!basePermissions.contains(permission)) {
            return;
        }
        if(!SQLQuery.PERMISSIONS_DELETE.execute(type, identifier, permission)) {
            Eclipse.Log.severe("Could not remove a permission to the entity (type: %s) \"%s\".", type, identifier);
            return;
        }
        basePermissions.remove(permission);
    }

    /**
     * Gets all base permissions.
     * @return The base permissions, as a string set.
     */
    public Set<String> getBasePermissions() {
        return basePermissions;
    }
}
