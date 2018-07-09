package net.thenova.eclipse.command.commands.egroup.subcommands;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.command.core.AbstractSub;
import net.thenova.eclipse.command.core.Arguments;
import net.thenova.eclipse.permission.entity.Group;
import net.thenova.eclipse.response.responses.MessageResponse;
import net.thenova.eclipse.sql.SQLQuery;

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
public final class DefaultCommand extends AbstractSub {

    /**
     * Creates the command.
     */
    public DefaultCommand() {
        super("default", Arguments.Type.GROUP);
    }

    /**
     * Toggles the group's default value.
     * @param arguments The arguments.
     */
    @Override
    protected void handle(Arguments arguments) {
        Group group = arguments.getGroup(0);
        boolean newDefault = !group.isDefaultGroup();
        if(!SQLQuery.GROUP_DEFAULT.execute(newDefault, group.getName())) {
            Eclipse.Log.warn("Could not update default value for group \"%s\".", group.getName());
        }
        group.setDefaultGroup(newDefault);
        arguments.getContext().reply(new MessageResponse("The group's default setting has been set to: " + newDefault + "."));
    }

}
