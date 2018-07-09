package net.thenova.eclipse.command.core;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.permission.Permissions;
import net.thenova.eclipse.permission.entity.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
public abstract class AbstractSub extends AbstractCommand {

    private final Arguments.Type[] argumentTypes;

    /**
     * Creates a new abstract subcommand.
     * @param name The name of the command.
     * @param argumentTypes The argument types.
     */
    protected AbstractSub(String name, Arguments.Type... argumentTypes) {
        super(name, null);
        this.argumentTypes = argumentTypes;
    }

    /**
     * Handles the sub command.
     * @param arguments The arguments.
     */
    protected abstract void handle(Arguments arguments);

    /**
     * Handles the sub command.
     * @param context The command context.
     * cmd owo arg0 arg1
     *         1    2
     *
     */
    @Override
    public final void onCommand(Context context) {
        List<Object> entities = new ArrayList<>();
        if(argumentTypes.length != 0) {
            String[] args = context.getArguments();
            Permissions permissions = Eclipse.getInstance().getPermissions();

            for(int i = 0; i < argumentTypes.length; i++) {
                Arguments.Type type = argumentTypes[i];
                if(args.length < i + 1) {
                    context.reply(type.getProvide());
                    return;
                }
                String input = args[i];
                switch(type) {
                    case GROUP:
                    case PARENT:
                        Group group = permissions.getGroup(input.toLowerCase());
                        if(group == null) {
                            context.reply(type.getInvalid());
                            return;
                        }
                        entities.add(i, group);
                        break;
                    case USER:
                        UUID uuid = Permissions.fromName(input);
                        if(uuid == null) {
                            context.reply(type.getInvalid());
                            return;
                        }
                        entities.add(i, permissions.fetchUser(uuid));
                        break;
                    case PERMISSION:
                        entities.add(i, input);
                        break;
                }
            }
        }
        handle(new Arguments(context, entities));
    }

}
