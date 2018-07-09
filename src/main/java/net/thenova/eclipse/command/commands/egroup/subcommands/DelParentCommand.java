package net.thenova.eclipse.command.commands.egroup.subcommands;

import net.thenova.eclipse.command.core.AbstractSub;
import net.thenova.eclipse.command.core.Arguments;
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
public final class DelParentCommand extends AbstractSub {

    /**
     * Creates the command.
     */
    public DelParentCommand() {
        super("delparent", Arguments.Type.GROUP, Arguments.Type.PARENT);
    }

    /**
     * Deletes the parent.
     * @param arguments The arguments.
     */
    @Override
    protected void handle(Arguments arguments) {
        arguments.getGroup(0).removeParent(arguments.getGroup(1));
        arguments.getContext().reply(new MessageResponse("The parent group has been removed."));
    }

}
