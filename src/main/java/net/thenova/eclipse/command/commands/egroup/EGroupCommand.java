package net.thenova.eclipse.command.commands.egroup;

import net.md_5.bungee.api.ChatColor;
import net.thenova.eclipse.command.commands.egroup.subcommands.*;
import net.thenova.eclipse.command.core.AbstractCommand;
import net.thenova.eclipse.command.core.Context;
import net.thenova.eclipse.response.responses.BannerResponse;

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
public final class EGroupCommand extends AbstractCommand {

    /**
     * Creates the command.
     */
    public EGroupCommand() {
        super("egroup", ADMIN_PERMISSION, "egroups", "eg");
        subCommands.add(new ListCommand());
        subCommands.add(new InfoCommand());
        subCommands.add(new AddCommand());
        subCommands.add(new DelCommand());
        subCommands.add(new AddPermCommand());
        subCommands.add(new DelPermCommand());
        subCommands.add(new AddParentCommand());
        subCommands.add(new DelParentCommand());
        subCommands.add(new DefaultCommand());
    }

    /**
     * Shows a list of commands.
     * @param context The command context.
     */
    @Override
    public void onCommand(Context context) {
        context.reply(new BannerResponse());
        AbstractCommand.Util.sendBatch(context, ChatColor.YELLOW + "/egroup ",
                "list",
                "info <group>",
                "add <group>",
                "del <group>",
                "addperm <group> <permission>",
                "delperm <group> <permission>",
                "addparent <group> <parent>",
                "delparent <group> <parent>",
                "default <group>"
        );
        context.reply(new BannerResponse());
    }

}
