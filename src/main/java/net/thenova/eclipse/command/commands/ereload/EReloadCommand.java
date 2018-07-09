package net.thenova.eclipse.command.commands.ereload;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.command.core.AbstractCommand;
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
public final class EReloadCommand extends AbstractCommand {

    /**
     * Creates the command.
     */
    public EReloadCommand() {
        super("ereload", ADMIN_PERMISSION, "erefresh", "er");
    }

    /**
     * Re-caches everything.
     * @param context The command context.
     */
    @Override
    protected void onCommand(Context context) {
        Eclipse.getInstance().getPermissions().load();
        context.reply(new MessageResponse("Re-cached everything."));
    }

}
