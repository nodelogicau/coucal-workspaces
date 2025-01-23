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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/collections")
/**
 * The collections controller serves partial html for collections and provides
 * collection management features.
 */
public class CollectionsController {

    @Autowired
    private CollectionManager manager;

    /**
     * Return a list of available collections.
     * @return
     */
    @GetMapping("/")
    public String listCollections(Model model) {
        model.addAttribute("collections", manager.getCollections());
        return "list/collections";
    }

    /**
     * Add a new collection.
     * @param collection
     * @return
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String addCollection(@ModelAttribute("displayName") String collection, Model model) throws IOException {
        manager.addCollection(collection);
        return listCollections(model);
    }

//    @GetMapping("/{id}")
//    public String listEntries(@PathVariable(value="id") String collectionId, Model model) throws IOException {
//        ObjectCollection<?> collection = manager.getCollection(collectionId);
//        model.addAttribute("content", collection.getAll(collection.listObjectUIDs().toArray(new String[0])));
//        model.addAttribute("collection", collection);
//        return "collection-view";
//    }

    /**
     * Update an existing collection.
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public String updateCollection(Model model, HttpServletResponse response, @PathVariable String id) {
        response.addHeader("HX-Trigger", "collectionsRefresh");
        return listCollections(model);
    }

    /**
     * Delete a collection.
     * @param collectionId
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteCollection(@PathVariable(value="id") String collectionId, Model model) throws ObjectStoreException,
            IOException {
        manager.removeCollection(collectionId);
        return listCollections(model);
    }
}
