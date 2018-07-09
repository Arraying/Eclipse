package net.thenova.eclipse.command.commands.euser;

import net.md_5.bungee.api.ChatColor;
import net.thenova.eclipse.command.commands.euser.subcommands.*;
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
public final class EUserCommand extends AbstractCommand {

    /**
     * Creates the command.
     */
    public EUserCommand() {
        super("euser", ADMIN_PERMISSION, "eusers", "eu");
        subCommands.add(new InfoCommand());
        subCommands.add(new AddPermCommand());
        subCommands.add(new DelPermCommand());
        subCommands.add(new AddGroupCommand());
        subCommands.add(new DelGroupCommand());
        subCommands.add(new SetGroupCommand());
    }

    /**
     * Shows a list of commands.
     * @param context The command context.
     */
    @Override
    public void onCommand(Context context) {
        context.reply(new BannerResponse());
        AbstractCommand.Util.sendBatch(context, ChatColor.YELLOW + "/euser ",
                "info <user>",
                "addperm <user> <permission>",
                "delperm <user> <permission>",
                "addgroup <user> <group>",
                "delgroup <user> <group>",
                "setgroup <user> <group>"
        );
        context.reply(new BannerResponse());
    }

}
