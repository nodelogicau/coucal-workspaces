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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

/**
 * Application entry controller.
 */
@Controller
/**
 * The index controller serves full html pages for the following:
 * - application root
 * - workspace options
 * - profile settings
 */
public class IndexController extends AbstractLayoutController {

    private final CollectionManager collectionManager;

    private final EntityManager entityManager;

    public IndexController(@Autowired CollectionManager collectionManager, @Autowired EntityManager entityManager) {
        this.collectionManager = collectionManager;
        this.entityManager = entityManager;
    }

    @GetMapping("/")
    public String index(Model model) throws IOException {
        populateModelForLayout(model);
        return "view/workspace";
    }
}
