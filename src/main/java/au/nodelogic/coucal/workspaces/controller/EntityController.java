package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/entities")
public class EntityController {

    private final EntityManager entityManager;

    public EntityController(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/{kind}")
    public String listEntities(@PathVariable(name = "kind") String kind, Model model) throws IOException {
        model.addAttribute("entities", entityManager.getEntityCollection().getAll());
        return "entities/" + kind;
    }
}
