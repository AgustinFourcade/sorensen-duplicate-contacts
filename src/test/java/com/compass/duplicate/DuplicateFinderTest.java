package com.compass.duplicate;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DuplicateFinderTest {

    // sorensenDice
    @Test
    public void testSorensenDice_IdenticalStrings() {
        assertEquals(1.0, DuplicateFinder.sorensenDice("hello", "hello"), 0.0);
    }

    @Test
    public void testSorensenDice_CompletelyDifferent() {
        assertEquals(0.0, DuplicateFinder.sorensenDice("abc", "xyz"), 0.0);
    }

    @Test
    public void testSorensenDice_SingleCharPadding() {
        // "C" vs "C" → padding "_C", "C_" should give perfect match
        assertEquals(1.0, DuplicateFinder.sorensenDice("C", "C"), 0.0);
    }

    @Test
    public void testSorensenDice_EmptyStrings() {
        assertEquals(1.0, DuplicateFinder.sorensenDice("", ""), 0.0);
        assertEquals(0.0, DuplicateFinder.sorensenDice("", "abc"), 0.0);
    }

    @Test
    public void testSorensenDice_NullHandling() {
        assertEquals(1.0, DuplicateFinder.sorensenDice(null, null), 0.0);
        assertEquals(0.0, DuplicateFinder.sorensenDice(null, "abc"), 0.0);
    }

    // accuracyLabel
    @Test
    public void testAccuracyLabel_High() {
        assertEquals("High", DuplicateFinder.accuracyLabel(0.9));
    }

    @Test
    public void testAccuracyLabel_Medium() {
        assertEquals("Medium", DuplicateFinder.accuracyLabel(0.5));
    }

    @Test
    public void testAccuracyLabel_Low() {
        assertEquals("Low", DuplicateFinder.accuracyLabel(0.2));
    }

    // contactScore
    @Test
    public void testContactScore_SimilarContacts() {
        Contact c1 = new Contact("1", "Agustin", "Fourcade", "agussfourcade@example.com", "12345", "Cabildo Street");
        Contact c2 = new Contact("2", "Austin", "Furcade", "agussfourcade@example.com", "12345", "Cabildo st");

        double score = DuplicateFinder.contactScore(c1, c2);
        assertTrue("Expected high score", score > 0.75);
    }

    @Test
    public void testContactScore_DifferentContacts() {
        Contact c1 = new Contact("1", "Agustin", "Fourcade", "agussfourcade@example.com", "12345", "Cabildo Street");
        Contact c2 = new Contact("2", "Tomas", "Garcia", "tomas@example.com", "54321", "Libertador Street");

        double score = DuplicateFinder.contactScore(c1, c2);
        assertTrue("Expected low score", score < 0.3);
    }

    //findDuplicates
    @Test
    public void testFindDuplicates_ReturnsSortedByScore() {
        Contact c1 = new Contact("1", "Agustin", "Fourcade", "agussfourcade@example.com", "12345", "Cabildo Street");
        Contact c2 = new Contact("2", "Tomas", "Garcia", "tomas@example.com", "54321", "Libertador Street");
        Contact c3 = new Contact("3", "Juan", "Perez", "juan@example.com", "32154", "Maipu Street");

        List<Contact> contacts = List.of(c1, c2, c3);
        List<String> results = DuplicateFinder.findDuplicates(contacts);

        // Should contain 3 pairs: (1,2), (1,3), (2,3)
        assertEquals(3, results.size());

        // First pair should be (2,3) since they are more similar
        String firstRow = results.get(0);
        assertTrue("Expected the most similar pair (2,3) to appear first",
                firstRow.contains("2") && firstRow.contains("3"));
    }
}
