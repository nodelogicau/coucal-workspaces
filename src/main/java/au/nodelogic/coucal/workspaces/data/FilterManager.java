package au.nodelogic.coucal.workspaces.data;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FilterManager {

    @Autowired
    private FilterRepository filterRepository;

    public List<Filter> getFilters() {
        return filterRepository.findAll();
    }
}
