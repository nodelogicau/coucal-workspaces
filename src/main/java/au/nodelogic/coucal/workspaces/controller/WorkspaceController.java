package au.nodelogic.coucal.workspaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/workspaces")
public class WorkspaceController {

    @GetMapping("/recent")
    public String listRecent(Model model) throws IOException {
        return "workspaces/recent";
    }
}
