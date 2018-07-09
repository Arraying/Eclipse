package net.thenova.eclipse.command.core;

import net.thenova.eclipse.permission.entity.Group;
import net.thenova.eclipse.permission.entity.User;
import net.thenova.eclipse.response.responses.MessageResponse;

import java.util.List;

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
public final class Arguments {

    private final Context context;
    private final List<Object> entities;

    /**
     * Creates new arguments.
     * @param context The context.
     * @param entities A list of entities.
     */
    Arguments(Context context, List<Object> entities) {
        this.context = context;
        this.entities = entities;
    }

    /**
     * Gets the context.
     * @return The context.
     */
    public Context getContext() {
        return context;
    }

    /**
     * Gets a group by argument index.
     * @param index The index.
     * @return A group.
     */
    public Group getGroup(int index) {
        return (Group) entities.get(index);
    }

    /**
     * Gets the user by argument index.
     * @param index The index.
     * @return A user.
     */
    public User getUser(int index) {
        return (User) entities.get(index);
    }

    /**
     * Gets a permission by argument index.
     * @param index The index.
     * @return A permission string.
     */
    public String getPermission(int index) {
        return entities.get(index).toString();
    }

    public enum Type {

        /**
         * A group.
         */
        GROUP(MessageResponse.PROVIDE_GROUP, MessageResponse.INVALID_GROUP),

        /**
         * A parent, which is also a group.
         */
        PARENT(MessageResponse.PROVIDE_PARENT, MessageResponse.INVALID_GROUP),

        /**
         * A user.
         */
        USER(MessageResponse.PROVIDE_USER, MessageResponse.INVALID_USER),

        /**
         * A permission, which is a string.
         */
        PERMISSION(MessageResponse.PROVIDE_PERMISSION, null);

        private final MessageResponse provide;
        private final MessageResponse invalid;

        /**
         * Creates a new message response.
         * @param provide The response used to ask for the argument.
         * @param invalid The response used to mark the argument as invalid.
         */
        Type(MessageResponse provide, MessageResponse invalid) {
            this.provide = provide;
            this.invalid = invalid;
        }

        /**
         * Gets the provide message.
         * @return The message response.
         */
        public MessageResponse getProvide() {
            return provide;
        }

        /**
         * Gets the invalid message.
         * @return The message response.
         */
        public MessageResponse getInvalid() {
            return invalid;
        }

    }

}
