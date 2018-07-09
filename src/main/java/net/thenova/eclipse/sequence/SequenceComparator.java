package net.thenova.eclipse.sequence;

import java.util.Comparator;

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
public final class SequenceComparator implements Comparator<Sequence> {

    /**
     * Compares two sequences.
     * This will determine which one has a higher priority.
     * @param o1 The first sequence.
     * @param o2 The second sequence.
     * @return The object with higher priority.
     */
    public int compare(Sequence o1, Sequence o2) {
        return Integer.compare(o1.getPriority(), o2.getPriority());
    }

}
