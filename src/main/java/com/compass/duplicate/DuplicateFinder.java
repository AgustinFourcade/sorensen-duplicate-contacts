package com.compass.duplicate;

import java.util.*;

public class DuplicateFinder {
    // Sørensen–Dice coefficient (bigrams based)
    // the algorithm is based on https://en.wikipedia.org/wiki/Dice_coefficient
    public static double sorensenDice(String s1, String s2) {
        if (s1 == null && s2 == null) return 1.0;
        if (s1 == null || s2 == null) return 0.0;
        if (s1.isBlank() && s2.isBlank()) return 1.0;
        if (s1.isBlank() || s2.isBlank()) return 0.0;

        // Add padding "_" at the beginning and end of the string to ensure at least one bigram is generated.
        // This prevents NaN results when input length < 2 (ej: "C" or "F").
        s1 = "_" + s1.toLowerCase() + "_";
        s2 = "_" + s2.toLowerCase() + "_";

        Set<String> bigrams1 = new HashSet<>();
        for (int i = 0; i < s1.length() - 1; i++) {
            bigrams1.add(s1.substring(i, i + 2));
        }

        Set<String> bigrams2 = new HashSet<>();
        for (int i = 0; i < s2.length() - 1; i++) {
            bigrams2.add(s2.substring(i, i + 2));
        }

        int intersection = 0;
        for (String bg : bigrams1) {
            if (bigrams2.contains(bg)) intersection++;
        }

        // Sørensen–Dice: (2 * intersection) / (elemA + elemB)
        return (2.0 * intersection) / (bigrams1.size() + bigrams2.size());
    }

    public static double contactScore(Contact c1, Contact c2) {
        double fn = sorensenDice(c1.getFirstName(), c2.getFirstName());
        double ln = sorensenDice(c1.getLastName(), c2.getLastName());
        double em = sorensenDice(c1.getEmail(), c2.getEmail());
        double zp = sorensenDice(c1.getZip(), c2.getZip());
        double ad = sorensenDice(c1.getAddress(), c2.getAddress());

        return (fn + ln + em + zp + ad) / 5.0;
    }

    public static String accuracyLabel(double score) {
        if (score >= 0.75) return "High";
        if (score >= 0.4) return "Medium";
        return "Low";
    }

    public static List<String> findDuplicates(List<Contact> contacts) {
        List<String[]> tempResults = new ArrayList<>();

        for (int i = 0; i < contacts.size(); i++) {
            for (int j = i + 1; j < contacts.size(); j++) {
                Contact c1 = contacts.get(i);
                Contact c2 = contacts.get(j);
                double score = contactScore(c1, c2);
                String acc = accuracyLabel(score);

                // Only include matches above this threshold.
                // Adjust the value (ej: 0.0, 0.5, 0.7) to control how strict the duplicate detection is.
                if (score > 0.0) { //0.0 shows all pairs
                    tempResults.add(new String[]{c1.getContactId(), c2.getContactId(), acc, String.valueOf(score)});
                }
            }
        }

        tempResults.sort((a, b) -> Double.compare(Double.parseDouble(b[3]), Double.parseDouble(a[3])));

        // Format into aligned table rows
        List<String> results = new ArrayList<>();
        for (String[] r : tempResults) {
            results.add(String.format("%-22s %-18s %-10s", r[0], r[1], r[2]));
        }

        return results;
    }
}