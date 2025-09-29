package org.reverence.jcommander.history;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reverence.jcommander.settings.I18n;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TrackedObjectTest {

    private final String s1 = I18n.getText("noun.first");
    private final String s2 = I18n.getText("noun.second");
    private final String s3 = I18n.getText("noun.third");
    private TrackedObject<String> object;

    @BeforeEach
    public void init() {
        object = new TrackedObject<>();
    }

    @Test
    void testDefault() {
        assertNull(object.get());
    }

    @Test
    void testSet() {
        object.set(s1);
        assertEquals(s1, object.get());
    }

    @Test
    void testSetToItself() {
        object.set(s1);
        assertEquals(s1, object.get());
        object.set(s1);
        assertEquals(s1, object.get());

        assertThrows(NoSuchElementException.class, object::undo);
    }

    @Test
    void testUndoAll() {
        object.set(s1);
        object.set(s2);
        object.set(s3);

        assertEquals(s3, object.get());
        object.undo();
        assertEquals(s2, object.get());
        object.undo();
        assertEquals(s1, object.get());

        assertThrows(NoSuchElementException.class, object::undo);
    }

    @Test
    void testRedoAll() {
        object.set(s1);
        object.set(s2);
        object.set(s3);
        object.undo();
        object.undo();

        assertEquals(s1, object.get());
        object.redo();
        assertEquals(s2, object.get());
        object.redo();
        assertEquals(s3, object.get());

        assertThrows(NoSuchElementException.class, object::redo);
    }

    @Test
    void testUndoRedoUndo() {
        object.set(s1);
        object.set(s2);

        assertEquals(s2, object.get());
        object.undo();
        assertEquals(s1, object.get());
        object.redo();
        assertEquals(s2, object.get());
        object.undo();
        assertEquals(s1, object.get());
    }
}
