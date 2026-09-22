package internal.condor.reportes;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import org.junit.jupiter.api.Test;

class HarnessMarkerTest {
    @Test
    void noFailBuildMarker() {
        assertFalse(new File("FAIL_BUILD").exists());
    }
}
