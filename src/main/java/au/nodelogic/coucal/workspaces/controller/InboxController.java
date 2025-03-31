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

import au.nodelogic.coucal.workspaces.InboxManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/inbox")
public class InboxController extends AbstractLayoutController {

//    IMAPService imapService;
    private  final InboxManager inboxManager;

    public InboxController(@Autowired InboxManager inboxManager) {
        this.inboxManager = inboxManager;
    }

    @GetMapping("/")
    public String listInbox(Model model) throws IOException {
        populateModelForLayout("Coucal Inbox", model);
        model.addAttribute("inboxMessages", inboxManager.getInboxCollection().getAll());
        return "inbox/index";
    }
}
