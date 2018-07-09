package net.thenova.eclipse.command.commands.etest;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.command.core.AbstractCommand;
import net.thenova.eclipse.command.core.Context;
import net.thenova.eclipse.permission.Permissions;
import net.thenova.eclipse.response.responses.MessageResponse;

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
public final class ETestCommand extends AbstractCommand {

    public ETestCommand() {
        super("etest", ADMIN_PERMISSION, "et");
    }

    /**
     * Tests if the specified user has permission.
     * @param context The command context.
     */
    @Override
    public void onCommand(Context context) {
        if(context.getArguments().length < 1) {
            context.reply(MessageResponse.PROVIDE_PERMISSION);
            return;
        }
        String permission = context.getArguments()[0];
        UUID uuid;
        if(context.getArguments().length >= 2) {
            uuid = Permissions.fromName(context.getArguments()[1]);
        } else {
            if(context.getSender() instanceof ProxiedPlayer) {
                uuid = ((ProxiedPlayer) context.getSender()).getUniqueId();
            } else {
                context.reply(new MessageResponse("Only players can execute this command without any arguments; please provide a user."));
                return;
            }
        }
        if(uuid == null) {
            context.reply(MessageResponse.INVALID_USER);
            return;
        }
        context.reply(new MessageResponse(Eclipse.getInstance().getPermissions().fetchUser(uuid).hasPermission(permission) ?
                "The user has permission" :
                "The user does not have permission."
        ));
    }

}
