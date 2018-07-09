package net.thenova.eclipse.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.thenova.eclipse.Eclipse;
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
public final class PermissionListener implements Listener {

    /**
     * Checks whether the player has the specified permission.
     * @param event The event.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPermissionCheck(PermissionCheckEvent event) {
        String permission = event.getPermission().toLowerCase();
        Eclipse eclipse = Eclipse.getInstance();
        if(event.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            User user = eclipse.getPermissions().fetchUser(player.getUniqueId());
            if(user == null) {
                return;
            }
            event.setHasPermission(user.hasPermission(permission));
        }
    }

}
