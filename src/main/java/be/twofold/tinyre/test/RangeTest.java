package be.twofold.tinyre.test;

import java.util.*;
import java.util.stream.*;

public final class RangeTest {
    private static final Map<Integer, String> Categories = Map.ofEntries(
        Map.entry(0, "Cn"),
        Map.entry(1, "Lu"),
        Map.entry(2, "Ll"),
        Map.entry(3, "Lt"),
        Map.entry(4, "Lm"),
        Map.entry(5, "Lo"),
        Map.entry(6, "Mn"),
        Map.entry(7, "Me"),
        Map.entry(8, "Mc"),
        Map.entry(9, "Nd"),
        Map.entry(10, "Nl"),
        Map.entry(11, "No"),
        Map.entry(12, "Zs"),
        Map.entry(13, "Zl"),
        Map.entry(14, "Zp"),
        Map.entry(15, "Cc"),
        Map.entry(16, "Cf"),
        Map.entry(18, "Co"),
        Map.entry(19, "Cs"),
        Map.entry(20, "Pd"),
        Map.entry(21, "Ps"),
        Map.entry(22, "Pe"),
        Map.entry(23, "Pc"),
        Map.entry(24, "Po"),
        Map.entry(25, "Sm"),
        Map.entry(26, "Sc"),
        Map.entry(27, "Sk"),
        Map.entry(28, "So"),
        Map.entry(29, "Pi"),
        Map.entry(30, "Pf")
    );

    public static void main(String[] args) {
        Set<Character> set = Categories.values().stream()
            .map(s -> s.charAt(0))
            .collect(Collectors.toSet());

        System.out.println(set);

        IntSummaryStatistics statistics = new IntSummaryStatistics();
        Map<String, List<Integer>> pointsByType = new HashMap<>();
        for (int i = Character.MIN_CODE_POINT; i < Character.MAX_CODE_POINT; i++) {
            String type = Categories.get(Character.getType(i));
            pointsByType
                .computeIfAbsent(type, __ -> new ArrayList<>())
                .add(i);
            pointsByType
                .computeIfAbsent(type.substring(0, 1), __ -> new ArrayList<>())
                .add(i);
        }

        Map<String, List<Range>> ranges = pointsByType.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> mergeInts(e.getValue())));

        System.out.println(pointsByType.size());
    }

    public static List<Range> mergeInts(List<Integer> values) {
        if (values == null || values.isEmpty()) {
            return List.of();
        }

        ArrayList<Integer> sortedValues = new ArrayList<>(values);
        sortedValues.sort(Comparator.naturalOrder());

        int min = values.get(0);
        int max = values.get(0);
        List<Range> result = new ArrayList<>();
        for (int i = 1; i < sortedValues.size(); i++) {
            int value = sortedValues.get(i);
            if (value > max + 1) {
                result.add(Range.of(min, max));
                min = value;
                max = value;
            } else if (value > max) {
                max = value;
            }
        }
        result.add(Range.of(min, max));
        return List.copyOf(result);
    }

    public static List<Range> mergeRanges(List<Range> ranges) {
        if (ranges == null || ranges.isEmpty()) {
            return List.of();
        }

        ArrayList<Range> sortedRanges = new ArrayList<>(ranges);
        sortedRanges.sort(Comparator.naturalOrder());

        int min = ranges.get(0).getLower();
        int max = ranges.get(0).getUpper();
        List<Range> result = new ArrayList<>();
        for (int i = 1; i < sortedRanges.size(); i++) {
            Range range = sortedRanges.get(i);
            if (range.getLower() > max + 1) {
                result.add(Range.of(min, max));
                min = range.getLower();
                max = range.getUpper();
            } else if (range.getUpper() > max) {
                max = range.getUpper();
            }
        }
        result.add(Range.of(min, max));
        return List.copyOf(result);
    }
}
