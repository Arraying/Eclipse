package net.thenova.eclipse.command.commands.egroup.subcommands;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.command.core.AbstractSub;
import net.thenova.eclipse.command.core.Arguments;
import net.thenova.eclipse.command.core.Context;
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
public final class DelCommand extends AbstractSub {

    /**
     * Creates the command.
     */
    public DelCommand() {
        super("del");
    }

    /**
     * Deletes a group.
     * @param arguments The arguments.
     */
    @Override
    protected void handle(Arguments arguments) {
        Context context = arguments.getContext();
        if(context.getArguments().length < 1) {
            context.reply(MessageResponse.PROVIDE_GROUP);
            return;
        }
        String group = context.getArguments()[0].toLowerCase();
        if(Eclipse.getInstance().getPermissions().getGroup(group) == null) {
            context.reply(MessageResponse.INVALID_GROUP);
            return;
        }
        Eclipse.getInstance().getPermissions().removeGroup(group);
        context.reply(new MessageResponse("The group has been deleted."));
    }

}
