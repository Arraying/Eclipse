package net.thenova.eclipse.response.responses;

import net.md_5.bungee.api.ChatColor;
import net.thenova.eclipse.response.Response;

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
public final class MessageResponse implements Response {

    public static final MessageResponse PROVIDE_USER = new MessageResponse("Please provide the name or UUID of the user.");
    public static final MessageResponse PROVIDE_GROUP = new MessageResponse("Please provide the name of the group.");
    public static final MessageResponse INVALID_USER = new MessageResponse("The user you provided does not exist.");
    public static final MessageResponse INVALID_GROUP = new MessageResponse("A group you provided does not exist.");
    public static final MessageResponse PROVIDE_PERMISSION = new MessageResponse("Please provide the permission.");
    public static final MessageResponse PROVIDE_PARENT = new MessageResponse("Please provide the name of the parent group.");
    public static final MessageResponse PERMISSION_ADDED = new MessageResponse("The permission node has been added.");
    public static final MessageResponse PERMISSION_DELETED = new MessageResponse("The permission node has been deleted.");

    private final String response;

    /**
     * Creates a new message response.
     * @param response The response message.
     */
    public MessageResponse(String response) {
        this.response = response;
    }

    /**
     * Gets the message.
     * @return The response.
     */
    @Override
    public String getMessage() {
        return ChatColor.GRAY + response;
    }

}
