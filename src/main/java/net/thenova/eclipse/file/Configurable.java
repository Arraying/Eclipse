package net.thenova.eclipse.file;

import net.md_5.bungee.config.Configuration;

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
@SuppressWarnings("ALL")
public interface Configurable {

    /**
     * Gets the file name for the specified configuration file.
     * @return The file name.
     */
    String getFileName();

    /**
     * Loads items from the BungeeCord configuration object.
     * @param configuration The configuration.
     * @return True if the load was successful, false otherwise.
     */
    boolean load(Configuration configuration);

}
