package TestUtilities;

import com.dskbot.utilities.Utilities;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UtilitiesTest {
    @Test
    public void testDeleteFile() {
        Utilities utilities = new Utilities();
        String path = "src/test/java/TestResources/testFile.txt";
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = utilities.deleteFile(path);
        assertEquals("Deleted file" + path, result);
        assertFalse(file.exists());
    }
    @Test
    public void testDeleteDir() {
        Utilities utilities = new Utilities();
        String path = "src/test/java/TestResources/testFolder";
        File file = new File(path);
        file.mkdirs();
        String result = utilities.deleteDir(file);
        assertEquals("Deleted folder", result);
        assertFalse(file.exists());
    }
    @Test
    public void testGetFolders() {
        Utilities utilities = new Utilities();
        String path = "src/test/java/TestResources";
        List<String> folders = utilities.getFolders(path);
        assertEquals(1, folders.size());
        assertEquals("src\\test\\java\\TestResources\\test", folders.get(0));
    }
    @Test
    public void testGetFoldersEmpty() {
        Utilities utilities = new Utilities();
        String path = "src/test/java/TestResources/testFile.txt";
        List<String> folders = utilities.getFolders(path);
        assertEquals(0, folders.size());
    }
    @Test
    public void testGetFoldersNull() {
        Utilities utilities = new Utilities();
        String path = "src/test/java/TestResources/testFolder";
        List<String> folders = utilities.getFolders(path);
        assertEquals(0, folders.size());
    }
    @Test
    public void testGetFoldersNullEmpty() {
        Utilities utilities = new Utilities();
        String path = "src/test/java/TestResources/testFolder";
        List<String> folders = utilities.getFolders(path);
        assertEquals(0, folders.size());
    }
}
