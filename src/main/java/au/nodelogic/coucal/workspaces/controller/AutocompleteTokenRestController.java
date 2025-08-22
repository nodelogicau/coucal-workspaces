package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.data.AutocompleteToken;
import au.nodelogic.coucal.workspaces.data.AutocompleteTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autocomplete-tokens")
public class AutocompleteTokenRestController {

    private final AutocompleteTokenRepository autocompleteTokenRepository;

    public AutocompleteTokenRestController(@Autowired AutocompleteTokenRepository autocompleteTokenRepository) {
        this.autocompleteTokenRepository = autocompleteTokenRepository;
    }

    @GetMapping
    public List<AutocompleteToken> getAutocompleteTokens(Model model) {
        return autocompleteTokenRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAutocompleteToken(@RequestParam("fieldName") String fieldName, @RequestParam("token") String token, Model model) {
        AutocompleteToken autocompleteToken = new AutocompleteToken();
        autocompleteToken.setFieldName(fieldName);
        autocompleteToken.setToken(token);
        autocompleteTokenRepository.save(autocompleteToken);
    }
}
