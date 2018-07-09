package net.thenova.eclipse.sequence;

import net.thenova.eclipse.Eclipse;
import net.thenova.eclipse.sequence.implementations.BungeeBoot;
import net.thenova.eclipse.sequence.implementations.DatabaseBoot;
import net.thenova.eclipse.sequence.implementations.FileBoot;
import net.thenova.eclipse.sequence.implementations.PermissionsBoot;

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
public interface Sequence {

    /**
     * Gets all boot sequences.
     * @return An array of sequences.
     */
    static Sequence[] getSequences() {
        return new Sequence[] {
                new FileBoot(),
                new DatabaseBoot(),
                new BungeeBoot(),
                new PermissionsBoot(),
        };
    }

    /**
     * Gets the priority of the boot sequence.
     * The higher the number, the higher the priority.
     * @return The priority.
     */
    int getPriority();

    /**
     * Starts the sequence.
     * @param eclipse The Eclipse instance.
     * @return True if the startup was successful, otherwise false to cancel further boot.
     */
    boolean start(Eclipse eclipse);

}
