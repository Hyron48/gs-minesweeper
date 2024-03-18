package it.unimol.minesweeper.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {

    @Test
    public void testConstructor() {
        Box box = new Box(true);
        assertTrue(box.isMined());
        assertFalse(box.isExplored());
        assertFalse(box.getAppearance().equals("X")); // Appearance is not set yet
        assertFalse(box.isMarked());

        Box emptyBox = new Box(false);
        assertFalse(emptyBox.isMined());
        assertFalse(emptyBox.isExplored());
        assertFalse(emptyBox.getAppearance().equals("X")); // Appearance is not set yet
        assertFalse(emptyBox.isMarked());
    }

    @Test
    public void testExplore() {
        Box box = new Box(true);
        box.explore();
        assertTrue(box.isExplored());
        assertEquals("X", box.getAppearance()); // Explored mined box should have appearance "X"

        Box emptyBox = new Box(false);
        emptyBox.explore();
        assertTrue(emptyBox.isExplored());
        assertEquals(" ", emptyBox.getAppearance()); // Explored empty box should have appearance " "
    }

    @Test
    public void testMark() {
        Box box = new Box(true);
        box.mark();
        assertTrue(box.isMarked());
        assertEquals("#", box.getAppearance()); // Marked box should have appearance "#"
    }

    @Test
    public void testUnmark() {
        Box box = new Box(true);
        box.mark();
        box.unmark();
        assertFalse(box.isMarked());
        assertEquals("-", box.getAppearance()); // Unmarked box should have appearance "-"
    }

    @Test
    public void testSetAppearance() {
        Box box = new Box(true);
        box.setAppearance("custom");
        assertEquals("custom", box.getAppearance());
    }

    @Test
    public void testGetAppearance() {
        Box box = new Box(true);
        assertEquals("-", box.getAppearance()); // Default appearance for unexplored box
        box.mark();
        assertEquals("#", box.getAppearance()); // Marked box should have appearance "#"
        box.explore();
        assertEquals("X", box.getAppearance()); // Explored mined box should have appearance "X"
    }
}