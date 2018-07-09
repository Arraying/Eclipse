package net.thenova.eclipse.command.commands.egroup.subcommands;

import net.md_5.bungee.api.ChatColor;
import net.thenova.eclipse.command.core.AbstractSub;
import net.thenova.eclipse.command.core.Arguments;
import net.thenova.eclipse.command.core.Context;
import net.thenova.eclipse.permission.entity.Group;
import net.thenova.eclipse.response.responses.BannerResponse;
import net.thenova.eclipse.response.responses.ListResponse;
import net.thenova.eclipse.response.responses.MessageResponse;

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
public final class InfoCommand extends AbstractSub {

    /**
     * Creates the command.
     */
    public InfoCommand() {
        super("info", Arguments.Type.GROUP);
    }

    /**
     * Shows information on the group.
     * @param arguments The arguments.
     */
    @Override
    protected void handle(Arguments arguments) {
        Context context = arguments.getContext();
        Group group = arguments.getGroup(0);
        context.reply(new BannerResponse());
        context.reply(new MessageResponse(ChatColor.YELLOW + "Name:"));
        context.reply(new MessageResponse(group.getName()));
        context.reply(new MessageResponse(ChatColor.YELLOW + "Default:"));
        context.reply(new MessageResponse(String.valueOf(group.isDefaultGroup())));
        context.reply(new MessageResponse(ChatColor.YELLOW + "Parents:"));
        for(String parent : group.getParents()) {
            context.reply(new ListResponse(parent));
        }
        context.reply(new MessageResponse(ChatColor.YELLOW + "Permissions:"));
        for(String permission : group.getBasePermissions()) {
            context.reply(new ListResponse(permission));
        }
        context.reply(new BannerResponse());
    }

}
