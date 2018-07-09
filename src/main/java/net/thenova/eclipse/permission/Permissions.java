package net.thenova.eclipse.permission;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.permission.entity.Group;
import net.thenova.eclipse.permission.entity.User;
import net.thenova.eclipse.sql.SQLHandler;
import net.thenova.eclipse.sql.SQLQuery;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;

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
public final class Permissions {

    private static final Pattern pattern = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
    private final Map<String, Group> groups = new ConcurrentHashMap<>();
    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    /**
     * Loads in all the groups.
     */
    public void load() {
        try {
            groups.clear();
            users.clear();
            ResultSet resultSet = SQLQuery.GROUPS_GET.result();
            if(resultSet == null) {
                Eclipse.Log.severe("Could not load in groups.");
                return;
            }
            while(resultSet.next()) {
                String groupName = resultSet.getString(SQLQuery.Info.GROUPS_NAME);
                Group group = new Group(groupName);
                group.load();
                if(resultSet.getBoolean(SQLQuery.Info.GROUPS_DEFAULT)) {
                    group.setDefaultGroup(true);
                }
                groups.put(groupName, group);
            }
            SQLHandler.close(resultSet);
            for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                loadUser(player.getUniqueId());
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Creates a new group.
     * @param name The name of the group.
     */
    public void createGroup(String name) {
        if(!SQLQuery.GROUP_CREATE.execute(name, false)) {
            Eclipse.Log.severe("Failed to add group \"%s\" to the database.", name);
        }
        Group group = new Group(name);
        groups.put(name, group);
    }

    /**
     * Deletes a group via name.
     * @param name The name of the group.
     */
    public void removeGroup(String name) {
        if(!SQLQuery.GROUP_DELETE.execute(name)) {
            Eclipse.Log.severe("Failed to delete group \"%s\" from the database.", name);
        }
        groups.remove(name);
    }

    /**
     * Gets a group via name.
     * @param name The name of the group.
     * @return The group, or null if it does not exist.
     */
    public Group getGroup(String name) {
        return groups.get(name);
    }

    /**
     * Gets all groups.
     * @return A collection of group objects.
     */
    public Collection<Group> getGroups() {
        return groups.values();
    }

    /**
     * Loads a user.
     * @param uuid The UUID of the user.
     * @return The user loaded.
     */
    public User loadUser(UUID uuid) {
        User user = new User(uuid);
        user.load();
        users.put(uuid, user);
        return user;
    }

    /**
     * Unloads a user.
     * @param uuid The UUID of the user.
     */
    public void unloadUser(UUID uuid) {
        users.remove(uuid);
    }

    /**
     * Fetches a user. If the user is cached, they will be used.
     * Otherwise, a SQL query will be executed and they will be loaded.
     * @param uuid The UUID of the user.
     * @return The user.
     */
    public User fetchUser(UUID uuid) {
        User user = users.get(uuid);
        if(user != null) {
            return user;
        }
        user = loadUser(uuid);
        unloadUser(uuid);
        return user;
    }

    /**
     * Gets a user via UUID. Unlike {@link #fetchUser(UUID, Consumer)} this will return null if the user does not exist.
     * @param uuid The UUID of the user.
     * @return The user, or null.
     */
    @Deprecated
    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    /**
     * Fetches the user and executes a task with the object.
     * @param uuid The UUID of the user.
     * @param consumer The consumer.
     */
    @Deprecated
    public void fetchUser(UUID uuid, Consumer<User> consumer) {
        User user = users.get(uuid);
        if(user == null) {
            user = loadUser(uuid);
            consumer.accept(user);
            unloadUser(uuid);
        } else {
            consumer.accept(user);
        }
    }

    /**
     * Gets the UUID from a Minecraft name.
     * The API has a ratelimit of 600 requests / 10 minutes.
     * @param name The name of the player.
     * @return The UUID, or null if it does not exist.
     */
    public static UUID fromName(String name) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
        if(player != null) {
            return player.getUniqueId();
        }
        try {
            String content = IOUtils.toString(new URL("https://api.mojang.com/users/profiles/minecraft/" + name), Charset.forName("utf8"));
            String uuidRaw = ((JsonObject) new JsonParser().parse(content)).get("id").toString().replace("\"", "");
            String uuid = pattern.matcher(uuidRaw).replaceAll("$1-$2-$3-$4-$5");
            return UUID.fromString(uuid);
        } catch(IOException | ClassCastException exception) {
            return null;
        }
    }

}
