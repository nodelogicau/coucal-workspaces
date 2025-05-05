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
import net.fortuna.ical4j.filter.FilterExpression;
import net.fortuna.ical4j.model.Calendar;
import org.ical4j.connector.ObjectCollection;
import org.ical4j.connector.ObjectStoreException;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/collections")
/**
 * The collections controller serves partial html for collections and provides
 * collection management features.
 */
public class CollectionsController {

    private final CollectionManager manager;

    public CollectionsController(@Autowired CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Return a list of available collections.
     * @return
     */
    @GetMapping("/")
    public String listCollections(@RequestParam(name = "filter", required = false) String filter, Model model) {
        List<ObjectCollection<?>> collectionList = manager.getCollections();
        if (filter != null) {
            model.addAttribute("collections",
                    collectionList.stream().filter(collection ->
                            collection.getDisplayName().contains(filter)).toList());
            model.addAttribute("createCollection", filter);
        } else {
            model.addAttribute("collections", manager.getCollections());
        }
        return "collections/list";
    }

    @GetMapping("/{id}")
    public String viewEntries(@PathVariable(name = "id") String collectionId,
                              @RequestParam(name = "concept", required = false) String[] concept,
                              Model model) throws IOException {
        ObjectCollection<Calendar> collection = manager.getCollection(collectionId);
        if (concept != null && concept.length > 0) {
            model.addAttribute("content",
                    collection.query(FilterExpression.parse(
                            String.format("concept in [%s]", String.join(",", concept)))));
        } else {
            model.addAttribute("content",
                    collection.getAll(collection.listObjectUIDs().toArray(new String[0])));
        }
        model.addAttribute("collection", collection);
        model.addAttribute("dateFormatter", new PrettyTime());
        model.addAttribute("columnHeadings", Arrays.asList("Summary", "Location", "Categories", "Date", "Status"));
        return "collections/entries";
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
        return listCollections(null, model);
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
        return listCollections(null, model);
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
        return listCollections(null, model);
    }
}
