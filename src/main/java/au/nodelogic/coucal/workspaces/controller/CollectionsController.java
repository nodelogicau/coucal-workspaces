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

import au.nodelogic.coucal.workspaces.CollectionManager;
import jakarta.servlet.http.HttpServletResponse;
import org.ical4j.connector.ObjectStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Controller
public class CollectionsController {

    @Autowired
    private CollectionManager manager;

    /**
     * Return a list of available collections.
     * @return
     */
    @PostMapping("/listCollections")
    public String list(Model model) {
        model.addAttribute("collections", manager.getCollections());
        return "collection-list";
    }

    /**
     * Add a new collection.
     * @param collection
     * @return
     */
    @PostMapping("/addCollection")
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@ModelAttribute("displayName") String collection, Model model) throws IOException {
        manager.addCollection(collection);
        return list(model);
    }

    /**
     * Update an existing collection.
     * @param collection
     * @return
     */
    public String update(String collection, Model model, HttpServletResponse response) {
        response.addHeader("HX-Trigger", "collectionsRefresh");
        return list(model);
    }

    /**
     * Delete a collection.
     * @param collection
     * @return
     */
    @PostMapping("/removeCollection")
    public String delete(String collection, Model model) throws ObjectStoreException, IOException {
        manager.removeCollection(collection);
        return list(model);
    }
}
