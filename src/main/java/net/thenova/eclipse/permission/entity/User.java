package net.thenova.eclipse.permission.entity;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.permission.AbstractPermissible;
import net.thenova.eclipse.sql.SQLHandler;
import net.thenova.eclipse.sql.SQLQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
public final class User extends AbstractPermissible {

    private final UUID uuid;
    private final Set<String> groups = new HashSet<>();

    /**
     * Creates a new user.
     * @param uuid The UUID of the user.
     */
    public User(UUID uuid) {
        super(SQLQuery.Info.PERMISSIONS_TYPE_USER, uuid.toString());
        this.uuid = uuid;
        load();
    }

    /**
     * Whether or not the user has the permission.
     * @param permission The permission.
     * @return True if they do, false otherwise.
     */
    @Override
    public boolean hasPermission(String permission) {
        if(basePermissions.contains(permission)) {
            return true;
        }
        for(String groupName : groups) {
            Group group = Eclipse.getInstance().getPermissions().getGroup(groupName);
            if(group == null) {
                Eclipse.Log.warn("The user \"%s\" is a member of nonexistent group \"%s\".", uuid, groupName);
                continue;
            }
            if(group.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loads in all the user's groups.
     */
    @Override
    public void loadMore() {
        try {
            ResultSet resultSet = SQLQuery.USER_GET_GROUPS.result(uuid.toString());
            if(resultSet == null) {
                Eclipse.Log.severe("Could not fetch groups for the user \"%s\".", uuid);
                return;
            }
            while(resultSet.next()) {
                groups.add(resultSet.getString(SQLQuery.Info.MEMBERSHIPS_GROUP));
            }
            SQLHandler.close(resultSet);
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Adds a group to the user.
     * @param group The group.
     */
    public void addGroup(Group group) {
        if(!groups.contains(group.getName())) {
            if(!SQLQuery.USER_ADD_GROUP.execute(uuid.toString(), group.getName())) {
                Eclipse.Log.severe("Could not add group \"%s\" to the user \"%s\".", uuid.toString(), group.getName());
                return;
            }
            groups.add(group.getName());
        }
    }

    /**
     * Removes a group from the user.
     * @param group The user.
     */
    public void removeGroup(Group group) {
        if(groups.contains(group.getName())) {
            if(!SQLQuery.USER_DELETE_GROUP.execute(uuid.toString(), group.getName())) {
                Eclipse.Log.severe("Could not remove group \"%s\" from the user \"%s\".", uuid.toString(), group.getName());
                return;
            }
            groups.remove(group.getName());
        }
    }

    /**
     * Sets the group of the user.
     * @param group The group.
     */
    public void setGroup(Group group) {
        List<Group> purging = new ArrayList<>();
        for(String groupName : groups) {
            Group potentialRemove = Eclipse.getInstance().getPermissions().getGroup(groupName);
            if(potentialRemove != null
                    && !potentialRemove.equals(group)) {
                purging.add(potentialRemove);
            }
        }
        for(Group purge : purging) {
            removeGroup(purge);
        }
        if(!groups.contains(group.getName())) {
            addGroup(group);
        }
    }

    /**
     * Gets the UUID of the user.
     * @return The user.
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Gets all groups.
     * @return A set of group names.
     */
    public Set<String> getGroups() {
        return groups;
    }

}
