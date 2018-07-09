package net.thenova.eclipse.command.core;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.thenova.eclipse.response.responses.MessageResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
public abstract class AbstractCommand extends Command {

    protected static final String ADMIN_PERMISSION = "eclipse.admin";
    protected final Set<AbstractSub> subCommands = new HashSet<>();

    /**
     * Creates a new abstract command.
     * @param name The name of the command.
     * @param permission The command permissions.
     * @param aliases The aliases of the command.
     */
    protected AbstractCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    /**
     * What happens when the command is executed.
     * @param context The command context.
     */
    protected abstract void onCommand(Context context);

    /**
     * What happens when the command is executed.
     * @param commandSender The command sender/
     * @param strings The arguments.
     */
    @Override
    public final void execute(CommandSender commandSender, String[] strings) {
        Context current = new Context(commandSender, strings);
        if(!subCommands.isEmpty()
                && strings.length != 0) {
            for(AbstractCommand command : subCommands) {
                if(command.getName().equalsIgnoreCase(strings[0])) {
                    command.onCommand(new Context(commandSender, strings.length == 1 ? new String[0] : Arrays.copyOfRange(strings, 1, strings.length)));
                    return;
                }
            }
            current.reply(new MessageResponse("The provided sub command does not correspond to any existing sub commands."));
            return;
        }
        onCommand(current);
    }

    public static final class Util {

        /**
         * Sends batch messages.
         * @param context The command context.
         * @param prefix The prefix.
         * @param messages The messages.
         */
        public static void sendBatch(Context context, String prefix, String... messages) {
            for(String message : messages) {
                context.reply(new MessageResponse(prefix + message));
            }
        }

    }

}
