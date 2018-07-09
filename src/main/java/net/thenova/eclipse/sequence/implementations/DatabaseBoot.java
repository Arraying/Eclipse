package net.thenova.eclipse.sequence.implementations;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.sequence.Sequence;
import net.thenova.eclipse.sql.SQLHandler;

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
public final class DatabaseBoot implements Sequence{

    /**
     * @return 16
     */
    @Override
    public int getPriority() {
        return 16;
    }

    /**
     * Attempts to connect to the database.
     * @param eclipse The Eclipse instance.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean start(Eclipse eclipse) {
        Eclipse.Log.info("Loading database connection...");
        return SQLHandler.getInstance().prepare();
    }

}
