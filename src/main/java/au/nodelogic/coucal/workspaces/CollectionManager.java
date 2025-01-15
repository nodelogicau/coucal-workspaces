/*
 *  Copyright 2025 Node Logic
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package au.nodelogic.coucal.workspaces;

import com.github.slugify.Slugify;
import org.ical4j.connector.ObjectCollection;
import org.ical4j.connector.ObjectStoreException;
import org.ical4j.connector.local.LocalCalendarCollection;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CollectionManager {

    private final File workspaceRoot;

    public CollectionManager() {
        this(new File(System.getProperty("user.dir"), "build/collections"));
    }

    public CollectionManager(File workspaceRoot) {
        this.workspaceRoot = workspaceRoot;
    }

    public List<ObjectCollection<?>> getCollections() {
        return Arrays.stream(Objects.requireNonNull(workspaceRoot.listFiles())).map(f -> {
            try {
                return new LocalCalendarCollection(f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toUnmodifiableList());
    }

    public void addCollection(String displayName) throws IOException {
        String dirSlug = Slugify.builder().build().slugify(displayName);
        LocalCalendarCollection newCol = new LocalCalendarCollection(new File(workspaceRoot, dirSlug));
        newCol.setDisplayName(displayName);
    }

    public void removeCollection(String displayName) throws IOException, ObjectStoreException {
        String dirSlug = Slugify.builder().build().slugify(displayName);
        LocalCalendarCollection col = new LocalCalendarCollection(new File(workspaceRoot, dirSlug));
        col.delete();
    }

    public <T> ObjectCollection<T> getCollection(String displayName) throws IOException {
        String dirSlug = Slugify.builder().build().slugify(displayName);
        return (ObjectCollection<T>) new LocalCalendarCollection(new File(workspaceRoot, dirSlug));
    }
}
