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

package au.nodelogic.coucal.workspaces.controller;

import org.springframework.stereotype.Controller;

@Controller
public class ContentController {

    /**
     * List collection content.
     * @return
     */
    public String list(String collection) {
        return "";
    }

    /**
     * Save new content.
     * @return
     */
    public String create(String collection) {
        return "";
    }

    /**
     * Update existing content.
     * @return
     */
    public String update(String collection, String uid) {
        return "";
    }

    /**
     * Remove content with the given UID.
     * @param uid
     * @return
     */
    public String delete(String collection, String uid) {
        return "";
    }
}
