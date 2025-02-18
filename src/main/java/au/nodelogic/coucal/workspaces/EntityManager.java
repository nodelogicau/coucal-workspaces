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

import org.ical4j.connector.CardCollection;
import org.ical4j.connector.local.LocalCardCollection;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class EntityManager extends AbstractWorkspaceManager {

    public EntityManager() {
        this(new File(System.getProperty("user.dir"), "build/collections"));
    }

    public EntityManager(File workspaceRoot) {
        super(workspaceRoot);
    }

    public CardCollection getEntityCollection() throws IOException {
        return (CardCollection) new LocalCardCollection(new File(getWorkspaceRoot(), "entities"));
    }
}
