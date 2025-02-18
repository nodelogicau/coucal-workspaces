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
import au.nodelogic.coucal.workspaces.data.EmailMessageRepository;
import au.nodelogic.coucal.workspaces.data.FeedItemRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.io.IOException;

/**
 * A layout controller serves complete html pages based on a common layout.
 */
public abstract class AbstractLayoutController {

    @Autowired
    private CollectionManager collectionManager;

    @Autowired
    private EntityManager entityManager;

    @Autowired
//    IMAPService imapService;
//    private InboxManager inboxManager;
    private EmailMessageRepository emailMessageRepository;

    @Autowired
    private FeedItemRepository feedItemRepository;

    protected void populateModelForLayout(Model model) throws IOException {
        populateModelForLayout("Coucal Workspaces", model);
    }

    /**
     * Update model to support common layout rendering.
     * @param model
     */
    protected void populateModelForLayout(String title, Model model) throws IOException {
        model.addAttribute("title", title);
        model.addAttribute("collections", collectionManager.getCollections());
        model.addAttribute("entities", entityManager.getEntityCollection().getAll());
        model.addAttribute("dateFormatter", new PrettyTime());
        model.addAttribute("inboxMessageCount", emailMessageRepository.count());
//        model.addAttribute("inbox", inboxManager.getInboxCollection().getAll());
        model.addAttribute("inbox", emailMessageRepository.findAll());
        model.addAttribute("feedItemCount", feedItemRepository.count());
    }
}
