//package be.twofold.tinyre;
//
//import com.google.common.collect.*;
//
//import java.util.*;
//import java.util.stream.*;
//
//final class AsciiTest {
//
//    private static final int[] Table = {
//        0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002,
//        0x0002, 0x2003, 0x2002, 0x2002, 0x2002, 0x2002, 0x0002, 0x0002,
//        0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002,
//        0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002, 0x0002,
//        0x6001, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004,
//        0xc004, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004,
//        0xd808, 0xd808, 0xd808, 0xd808, 0xd808, 0xd808, 0xd808, 0xd808,
//        0xd808, 0xd808, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004,
//        0xc004, 0xd508, 0xd508, 0xd508, 0xd508, 0xd508, 0xd508, 0xc508,
//        0xc508, 0xc508, 0xc508, 0xc508, 0xc508, 0xc508, 0xc508, 0xc508,
//        0xc508, 0xc508, 0xc508, 0xc508, 0xc508, 0xc508, 0xc508, 0xc508,
//        0xc508, 0xc508, 0xc508, 0xc004, 0xc004, 0xc004, 0xc004, 0xc004,
//        0xc004, 0xd608, 0xd608, 0xd608, 0xd608, 0xd608, 0xd608, 0xc608,
//        0xc608, 0xc608, 0xc608, 0xc608, 0xc608, 0xc608, 0xc608, 0xc608,
//        0xc608, 0xc608, 0xc608, 0xc608, 0xc608, 0xc608, 0xc608, 0xc608,
//        0xc608, 0xc608, 0xc608, 0xc004, 0xc004, 0xc004, 0xc004, 0x0002,
//    };
//
//    enum Ctype {
//        Blank(0x0001, 0x0400),
//        Cntrl(0x0002, 0x0100),
//        Punct(0x0004, 0x0200),
//        Alnum(0x0008, 0),
//        Upper(0x0100, 0x0010),
//        Lower(0x0200, 0x0020),
//        Alpha(0x0400, 0),
//        Digit(0x0800, 0x0040),
//        Xdigit(0x1000, 0x0800),
//        Space(0x2000, 0x0080),
//        Print(0x4000, 0),
//        Graph(0x8000, 0);
//
//        private final int oldMask;
//        private final int newMask;
//
//        Ctype(int oldMask, int newMask) {
//            this.oldMask = oldMask;
//            this.newMask = newMask;
//        }
//    }
//
//    public static void generateNewTable() {
//        String s = "" +
//            "\u0100\u0100\u0100\u0100\u0100\u0100\u0100\u0100" +
//            "\u0100\u0980\u0180\u0180\u0180\u0180\u0100\u0100" +
//            "\u0100\u0100\u0100\u0100\u0100\u0100\u0100\u0100" +
//            "\u0100\u0100\u0100\u0100\u0100\u0100\u0100\u0100" +
//            "\u0880\u0200\u0200\u0200\u0200\u0200\u0200\u0200" +
//            "\u0200\u0200\u0200\u0200\u0200\u0200\u0200\u0200" +
//            "\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447" +
//            "\u0448\u0449\u0200\u0200\u0200\u0200\u0200\u0200" +
//            "\u0200\u041a\u041b\u041c\u041d\u041e\u041f\u0010" +
//            "\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010" +
//            "\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010" +
//            "\u0010\u0010\u0010\u0200\u0200\u0200\u0200\u0200" +
//            "\u0200\u042a\u042b\u042c\u042d\u042e\u042f\u0020" +
//            "\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020" +
//            "\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020" +
//            "\u0020\u0020\u0020\u0200\u0200\u0200\u0200\u0100";
//
//        for (int i = 0; i < Table.length; i++) {
//            int c = Table[i];
//            int newC = 0;
//            for (Ctype ctype : Ctype.values()) {
//                if ((c & ctype.oldMask) != 0) {
//                    newC |= ctype.newMask;
//                }
//            }
//            if (i >= '0' && i <= '9' || i >= 'a' && i <= 'f' || i >= 'A' && i <= 'F') {
//                int digit = Character.digit(i, 16);
//                newC |= digit;
//            }
//
//            if(i % 8 == 0){
//                System.out.println();
//            }
//            System.out.printf("\\u%04x", newC);
//        }
//        System.out.println();
//    }
//
//    public static void main(String[] args) {
//        generateNewTable();
////        if (true) {
////            return;
////        }
//
//        Map<Ctype, BitSet> categories = new TreeMap<>();
//
//        for (int c = 0; c < Table.length; c++) {
//            for (Ctype ctype : Ctype.values()) {
//                if ((Table[c] & ctype.oldMask) != 0) {
//                    categories
//                        .computeIfAbsent(ctype, __ -> new BitSet())
//                        .set(c);
//                }
//            }
//        }
//
//        for (Map.Entry<Ctype, BitSet> entry : categories.entrySet()) {
//            System.out.println(entry.getKey() + " \t--> " + entry.getValue());
//        }
//
//        Set<Set<Map.Entry<Ctype, BitSet>>> powerSet = Sets.powerSet(categories.entrySet());
//        System.out.println(powerSet.size());
//
//        Map<Ctype, Set<Set<Ctype>>> simplifications = new TreeMap<>();
//        for (Set<Map.Entry<Ctype, BitSet>> entries : powerSet) {
//            int mask = entries.stream()
//                .map(Map.Entry::getKey)
//                .map(ctype1 -> ctype1.oldMask)
//                .reduce(0, (a, b) -> a | b);
//
//            BitSet combo = entries.stream()
//                .map(Map.Entry::getValue)
//                .reduce(new BitSet(), (a, b) -> {
//                    a.or(b);
//                    return a;
//                });
//
//            for (Ctype ctype : Ctype.values()) {
//                if ((ctype.oldMask & mask) != 0) {
//                    continue;
//                }
//                if (combo.equals(categories.get(ctype))) {
//                    Set<Ctype> ctypes = entries.stream().map(Map.Entry::getKey).collect(Collectors.toSet());
////                    System.out.print("Found simplification: ");
////                    System.out.print(ctype + " --> " + ctypes);
////                    System.out.println();
//                    simplifications
//                        .computeIfAbsent(ctype, __ -> new HashSet<>())
//                        .add(ctypes);
//                }
//            }
//        }
//
//        for (Map.Entry<Ctype, Set<Set<Ctype>>> entry : simplifications.entrySet()) {
//            int minSize = entry.getValue().stream().mapToInt(Set::size).min().getAsInt();
//            List<Set<Ctype>> minsizes = entry.getValue().stream().filter(set -> set.size() == minSize).collect(Collectors.toList());
//            System.out.println(entry.getKey() + "\t" + minsizes);
//        }
//
//        Set<Ctype> set = EnumSet.allOf(Ctype.class);
//        set.removeAll(simplifications.keySet());
//
//        Map<Ctype, Integer> sizes = categories.entrySet().stream()
//            .filter(e -> set.contains(e.getKey()))
//            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().cardinality()));
//
//        for (Map.Entry<Ctype, BitSet> entry : categories.entrySet()) {
//            System.out.println(entry.getKey() + "\t" + ranges(entry.getValue()));
//        }
//
//        System.out.println("sizes = " + sizes);
//    }
//
//    private static final class Range {
//        private final int min;
//        private final int max;
//
//        private Range(int min, int max) {
//            this.min = min;
//            this.max = max;
//        }
//
//        @Override
//        public String toString() {
//            return min + " -> " + max;
//        }
//    }
//
//    private static List<Range> ranges(BitSet bitSet) {
//        List<Range> ranges = new ArrayList<>();
//        int clear = 0;
//        while (true) {
//            int set = bitSet.nextSetBit(clear);
//            if (set < 0) {
//                break;
//            }
//            clear = bitSet.nextClearBit(set);
//            ranges.add(new Range(set, clear - 1));
//        }
//        return ranges;
//    }
//
//}
