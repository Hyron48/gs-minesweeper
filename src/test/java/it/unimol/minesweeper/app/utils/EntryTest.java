package it.unimol.minesweeper.app.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EntryTest {

    @Test
    public void testEntryCreation() {
        Entry entry = new Entry("Giuseppe", 100.0);
        assertNotNull(entry);
    }

    @Test
    public void testGetName() {
        Entry entry = new Entry("Paolo", 100.0);
        assertEquals("Paolo", entry.getName());
    }

    @Test
    public void testGetScore() {
        Entry entry = new Entry("Giovanni", 100.0);
        assertEquals(Double.valueOf(100.0), entry.getScore());
    }

    @Test
    public void testGetCreationDate() {
        Entry entry = new Entry("Giuseppe", 100.0);
        assertNotNull(entry.getCreationDate());
    }

    @Test
    public void testCreationDateAfterEntryCreation() {
        LocalDateTime beforeCreation = LocalDateTime.now();
        Entry entry = new Entry("Francesco", 100.0);
        LocalDateTime afterCreation = LocalDateTime.now();
        LocalDateTime creationDate = entry.getCreationDate();
        assertTrue(creationDate.isAfter(beforeCreation) || creationDate.isEqual(beforeCreation));
        assertTrue(creationDate.isBefore(afterCreation) || creationDate.isEqual(afterCreation));
    }
}
