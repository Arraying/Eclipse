package net.thenova.eclipse;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.thenova.eclipse.file.Configurable;
import net.thenova.eclipse.permission.Permissions;
import net.thenova.eclipse.sequence.Sequence;
import net.thenova.eclipse.sequence.SequenceComparator;
import net.thenova.eclipse.sql.SQLConfig;
import net.thenova.eclipse.sql.SQLHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.logging.Logger;

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
public final class Eclipse extends Plugin {

    private static Eclipse instance;
    private final PriorityQueue<Sequence> bootSequences = new PriorityQueue<>(Sequence.getSequences().length, new SequenceComparator());
    private final Permissions permissions = new Permissions();
    private final SQLConfig sqlConfig = new SQLConfig();

    /**
     * Populates the boot sequence queue.
     */
    public Eclipse() {
        bootSequences.addAll(Arrays.asList(Sequence.getSequences()));
    }

    /**
     * Gets the Eclipse instance the singleton way.
     * @return The instance. Should never be null.
     */
    public static Eclipse getInstance() {
        return instance;
    }

    /**
     * The method that is invoked when the plugin is enabled.
     * Loads all boot sequences.
     */
    @Override
    public void onEnable() {
        instance = this;
        Log.logger = getLogger();
        for(Sequence sequence : bootSequences) {
            if(!sequence.start(instance)) {
                getLogger().severe("Aborting startup.");
                break;
            }
        }
    }

    /**
     * The method that is invoked when the plugin is disabled.
     * Shuts down the database connections.
     */
    @Override
    public void onDisable() {
        SQLHandler.getInstance().shutdown();
    }

    /**
     * Loads all files and caches the content.
     * @return False if there was an error.
     */
    public boolean loadFiles() {
        //noinspection ResultOfMethodCallIgnored
        getDataFolder().mkdirs();
        ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
        for(Configurable configurable : new Configurable[]{ sqlConfig }) {
            File file = new File(getDataFolder(), configurable.getFileName());
            if(!createFile(file)) {
                return false;
            }
            Configuration configuration;
            try {
                configuration = provider.load(file);
            } catch(IOException exception) {
                Log.severe("Could not load configurable \"%s\".", configurable.getClass().getName());
                exception.printStackTrace();
                return false;
            }
            boolean success = configurable.load(configuration);
            if(!success) {
                Log.severe("Unsuccessful load for \"%s\".", configurable.getFileName());
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a file if it does not exist, and copies over defaults.
     * @param file The file.
     * @return True if successful, false otherwise.
     */
    private boolean createFile(File file) {
        try {
            if(!file.exists()) {
                try(InputStream inputStream = getResourceAsStream(file.getName())) {
                    if(inputStream == null) {
                        Log.warn("Could not create default for \"%s\".", file.getName());
                    } else {
                        Files.copy(inputStream, file.toPath());
                    }
                    return true;
                }
            }
        } catch(IOException exception) {
            Log.severe("Could not create file \"%s\"", file.getName());
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Gets the SQL configuration.
     * @return The SQL config.
     */
    public SQLConfig getSqlConfig() {
        return sqlConfig;
    }

    /**
     * Gets the core permissions class.
     * @return The core permissions class.
     */
    public Permissions getPermissions() {
        return permissions;
    }

    public static class Log {

        private static Logger logger = null;

        /**
         * Logs an info message.
         * @param message The message.
         * @param format Formatting objects.
         */
        public static void info(String message, Object... format) {
            assert logger != null;
            logger.info(String.format(message, (Object[]) format));
        }

        /**
         * Logs a warning message.
         * @param message The message.
         * @param format Formatting objects.
         */
        public static void warn(String message, Object... format) {
            assert logger != null;
            logger.warning(String.format(message, (Object[]) format));
        }

        /**
         * Logs a severe message.
         * @param message The message.
         * @param format Formatting objects.
         */
        public static void severe(String message, Object... format) {
            assert logger != null;
            logger.warning(String.format(message, (Object[]) format));
        }

    }

}
