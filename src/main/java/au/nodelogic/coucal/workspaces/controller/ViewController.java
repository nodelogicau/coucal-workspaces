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
import com.fasterxml.jackson.databind.ObjectMapper;
import net.fortuna.ical4j.filter.FilterExpression;
import org.ical4j.connector.ObjectCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/view")
/**
 * The view controller serves html page rendering for the following:
 * - Collections
 * - Entries
 * - Entities
 */
public class ViewController extends AbstractLayoutController {

    @Autowired
    private CollectionManager manager;

    @Autowired
    private ObjectMapper mapper;

    /**
     * List collection content.
     * @return
     */
    @GetMapping("/collection/{id}")
    public String list(@PathVariable(value="id") String collectionId, @RequestParam(name = "filter", required = false) String filter,
                       Model model) throws IOException {
        ObjectCollection<?> collection = manager.getCollection(collectionId);
        List<?> content;
        if (filter != null) {
            content = collection.query(FilterExpression.parse(filter));
        } else {
            content = collection.getAll(collection.listObjectUIDs().toArray(new String[0]));
        }
        model.addAttribute("content", content);
        model.addAttribute("collection", collection);
        model.addAttribute("collections", manager.getCollections());
        return "view/collection";
    }

}