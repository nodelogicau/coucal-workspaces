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
import au.nodelogic.coucal.workspaces.EntityManager;
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
import java.util.Arrays;
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

    private final CollectionManager manager;

    private final EntityManager entityManager;

    private final ObjectMapper mapper;

    public ViewController(@Autowired CollectionManager manager, @Autowired EntityManager entityManager,
                          @Autowired ObjectMapper mapper) {
        this.manager = manager;
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    @GetMapping("/workspace")
    public String viewWorkspace(Model model) throws IOException {
        populateModelForLayout(model);
        return "view/workspace";
    }

    @GetMapping("/inbox")
    public String viewInbox(Model model) throws IOException {
        populateModelForLayout(model);
        model.addAttribute("columnHeadings", Arrays.asList("Subject", "From", "Date"));
        return "view/inbox";
    }

    @GetMapping("/feeds")
    public String viewFeeds(Model model) throws IOException {
        populateModelForLayout(model);
//        model.addAttribute("columnHeadings", Arrays.asList("Subject", "From", "Date"));
        return "view/feeds";
    }

    @GetMapping("/chat")
    public String viewChat(Model model) throws IOException {
        populateModelForLayout(model);
//        model.addAttribute("columnHeadings", Arrays.asList("Subject", "From", "Date"));
        return "view/chat";
    }

    @GetMapping("/chat/{topic}")
    public String viewChatTopic(@PathVariable(value="topic") String topic, Model model) throws IOException {
        populateModelForLayout(model);
        return "view/chat-topic";
    }

    /**
     * List collection content.
     * @return
     */
    @GetMapping("/collection/{id}")
    public String viewCollection(@PathVariable(value="id") String collectionId,
                                 @RequestParam(name = "filter", required = false) String filter,
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
        populateModelForLayout(model);
        return "view/collection";
    }

    @GetMapping("/{concept}/{uid}")
    public String viewEntry(@PathVariable(name = "concept") String concept,
                            @PathVariable(name = "uid") String uid, Model model) throws IOException {
        populateModelForLayout(model);
        ObjectCollection<?> collection = manager.getCollectionForUid(uid);
        model.addAttribute("collection", collection);
        model.addAttribute("entry", collection.get(uid).orElseThrow());
        return "view/" + concept.replaceAll(":", "/");
    }

    @GetMapping("/entities/{kind}")
    public String viewEntities(@PathVariable(name = "kind") String kind, Model model) throws IOException {
        model.addAttribute("entities", entityManager.getEntityCollection().getAll());
        populateModelForLayout(model);
        return "view/entities";
    }

    @GetMapping("/entity/{uid}")
    public String viewEntity(@PathVariable(name = "uid") String uid, Model model) throws IOException {
        model.addAttribute("entity", entityManager.getEntityCollection().get(uid).orElseThrow());
        populateModelForLayout(model);
        return "view/entity";
    }
}
