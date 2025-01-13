package au.nodelogic.coucal.workspaces;

import com.github.slugify.Slugify;
import org.ical4j.connector.ObjectCollection;
import org.ical4j.connector.local.LocalCalendarCollection;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CollectionManager {

    private final File workspaceRoot;

    public CollectionManager(File workspaceRoot) {
        this.workspaceRoot = workspaceRoot;
    }

    public List<ObjectCollection<?>> getCollections() {
        return Arrays.stream(Objects.requireNonNull(workspaceRoot.listFiles())).map(f -> {
            try {
                return new LocalCalendarCollection(f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toUnmodifiableList());
    }

    public void addCollection(String displayName) throws IOException {
        String dirSlug = Slugify.builder().build().slugify(displayName);
        LocalCalendarCollection newCol = new LocalCalendarCollection(new File(workspaceRoot, dirSlug));
        newCol.setDisplayName(displayName);
    }
}
