package net.thenova.eclipse.permission.entity;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.permission.AbstractPermissible;
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
public final class Group extends AbstractPermissible {

    private final String name;
    private final Set<String> parents = new HashSet<>();
    private boolean defaultGroup = false;

    /**
     * Creates a new group.
     * @param name The name of the group.
     */
    public Group(String name) {
        super(SQLQuery.Info.PERMISSIONS_TYPE_GROUP, name);
        this.name = name;
    }

    /**
     * Whether or not the group has permission.
     * @param permission The permission.
     * @return True if it does, false otherwise.
     */
    @Override
    public boolean hasPermission(String permission) {
        if(basePermissions.contains(permission)) {
            return true;
        }
        for(String parent : parents) {
            Group group = Eclipse.getInstance().getPermissions().getGroup(parent);
            if(group == null) {
                Eclipse.Log.warn("The group \"%s\" lists the nonexistent group \"%s\" as their parent.", name, parent);
                continue;
            }
            if(group.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loads in all the parents.
     */
    @Override
    public void loadMore() {
        try {
            ResultSet resultSet = SQLQuery.GROUP_GET_PARENTS.result(name);
            if(resultSet == null) {
                Eclipse.Log.severe("Could not fetch parents for the group \"%s\".", name);
                return;
            }
            while(resultSet.next()) {
                parents.add(resultSet.getString(SQLQuery.Info.INHERITANCES_PARENT));
            }
            SQLHandler.close(resultSet);
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Adds a parent group.
     * @param group The group.
     */
    public void addParent(Group group) {
        if(!SQLQuery.GROUP_ADD_PARENT.execute(name, group.getName())) {
            Eclipse.Log.severe("Could not add parent \"%s\" to group \"%s\".", group.getName(), name);
            return;
        }
        parents.add(group.getName());
    }

    /**
     * Removes a parent group.
     * @param group The group.
     */
    public void removeParent(Group group) {
        if(!SQLQuery.GROUP_DELETE_PARENT.execute(name, group.getName())) {
            Eclipse.Log.severe("Could not delete parent \"%s\" from group \"%s\".", group.getName(), name);
            return;
        }
        parents.remove(group.getName());
    }

    /**
     * Gets the group name.
     * @return The name of the group.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets all parents.
     * @return A set of parent group names.
     */
    public Set<String> getParents() {
        return parents;
    }

    /**
     * Whether or not this group is a default group.
     * @return True if it is, false otherwise.
     */
    public boolean isDefaultGroup() {
        return defaultGroup;
    }

    /**
     * Sets the default group.
     * @param defaultGroup True if it is, false otherwise.
     */
    public void setDefaultGroup(boolean defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

}
