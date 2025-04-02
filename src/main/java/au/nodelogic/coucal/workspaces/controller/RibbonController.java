package au.nodelogic.coucal.workspaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/ribbon")
public class RibbonController {

    @GetMapping("/{context}/{tab}")
    public String getRibbon(@PathVariable(name = "context") String context,
                              @PathVariable(name = "tab") String tab, Model model) throws IOException {
        model.addAttribute("tab", tab);
        return context + "/ribbon";
    }
}
