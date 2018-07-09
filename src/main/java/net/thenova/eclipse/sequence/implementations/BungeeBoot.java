package net.thenova.eclipse.sequence.implementations;

import net.md_5.bungee.api.plugin.PluginManager;
import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.command.commands.egroup.EGroupCommand;
import net.thenova.eclipse.command.commands.ereload.EReloadCommand;
import net.thenova.eclipse.command.commands.etest.ETestCommand;
import net.thenova.eclipse.command.commands.euser.EUserCommand;
import net.thenova.eclipse.listeners.PermissionListener;
import net.thenova.eclipse.listeners.PlayerListener;
import net.thenova.eclipse.sequence.Sequence;

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
public final class BungeeBoot implements Sequence {

    /**
     * @return 32
     */
    @Override
    public int getPriority() {
        return 32;
    }

    /**
     * Registers all commands and listeners.
     * @param eclipse The Eclipse instance.
     * @return Always true.
     */
    @Override
    public boolean start(Eclipse eclipse) {
        PluginManager manager = eclipse.getProxy().getPluginManager();
        Eclipse.Log.info("Registering commands...");
        manager.registerCommand(eclipse, new EUserCommand());
        manager.registerCommand(eclipse, new EGroupCommand());
        manager.registerCommand(eclipse, new ETestCommand());
        manager.registerCommand(eclipse, new EReloadCommand());
        Eclipse.Log.info("Registering listeners...");
        manager.registerListener(eclipse, new PermissionListener());
        manager.registerListener(eclipse, new PlayerListener());
        return true;
    }

}
