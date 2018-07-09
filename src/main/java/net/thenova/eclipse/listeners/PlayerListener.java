package net.thenova.eclipse.listeners;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.permission.entity.Group;
import net.thenova.eclipse.permission.entity.User;

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
public final class PlayerListener implements Listener {

    private final Eclipse eclipse;

    /**
     * Sets the Eclipse instance.
     */
    public PlayerListener() {
        eclipse = Eclipse.getInstance();
    }

    /**
     * When the player joins the proxy.
     * Loads player data.
     * @param event The event.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PostLoginEvent event) {
        User user = eclipse.getPermissions().loadUser(event.getPlayer().getUniqueId());
        if(user.getGroups().isEmpty()) {
            for(Group group : Eclipse.getInstance().getPermissions().getGroups()) {
                if(group.isDefaultGroup()) {
                    user.addGroup(group);
                }
            }
        }
    }

    /**
     * When the player leaves the proxy.
     * Purges player data.
     * @param event The event.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerDisconnectEvent event) {
        eclipse.getPermissions().unloadUser(event.getPlayer().getUniqueId());
    }

}
