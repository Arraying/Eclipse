package net.thenova.eclipse.command.commands.egroup.subcommands;

import net.md_5.bungee.api.ChatColor;
import net.thenova.eclipse.Eclipse;
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
public final class ListCommand extends AbstractSub {

    /**
     * Creates the command.
     */
    public ListCommand() {
        super("list");
    }

    /**
     * Shows a list of groups.
     * @param arguments The arguments.
     */
    @Override
    protected void handle(Arguments arguments) {
        Context context = arguments.getContext();
        context.reply(new BannerResponse());
        context.reply(new MessageResponse(ChatColor.YELLOW + "Groups:"));
        for(Group group : Eclipse.getInstance().getPermissions().getGroups()) {
            context.reply(new ListResponse(group.getName()));
        }
        context.reply(new BannerResponse());
    }

}
