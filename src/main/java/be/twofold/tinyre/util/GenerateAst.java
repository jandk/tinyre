package be.twofold.tinyre.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

final class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: GenerateAst <output directory>");
            return;
        }

        defineAst(args[0], "Re", Arrays.asList(
            "AnyChar       : ",
            "CharRange     : char min, char max",
            "Complement    : Re expr",
            "Concatenation : Re left, Re right",
            "Intersection  : Re left, Re right",
            "Literal       : String s",
            "Predefined    : char identifier",
            "Repeat        : Re expr, int min, int max",
            "Union         : Re left, Re right"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        Files.createDirectories(Paths.get(outputDir));
        String path = outputDir + "/" + baseName + ".java";
        try (PrintWriter writer = new PrintWriter(path)) {

            writer.println("package be.twofold.tinyre;");
            writer.println();
            writer.println("public abstract class " + baseName + " {");
            writer.println();

            // The base accept() method.
            writer.println("    public abstract <R> R accept(Visitor<R> visitor);");
            writer.println();

            defineVisitor(writer, baseName, types);

            // The AST classes.
            for (String type : types) {
                String className = type.split(":")[0].trim();
                String fields = type.split(":")[1].trim();
                defineType(writer, baseName, className, fields);
            }

            writer.println("}");
        }
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("    public interface Visitor<R> {");

        for (String type : types) {
            writer.println();
            String typeName = type.split(":")[0].trim();
            writer.println("        R visit" + typeName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println();
        writer.println("    }");
        writer.println();
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.println("    public static class " + className + " extends " + baseName + " {");

        List<String> fields = Arrays.stream(fieldList.split(", "))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());

        // Fields.
        for (String field : fields) {
            writer.println("        public final " + field + ";");
        }
        writer.println();

        writer.println("        public " + className + "(" + fieldList + ") {");
        for (String s : fields) {
            String name = s.split(" ")[1];
            writer.println("            this." + name + " = " + name + ";");
        }
        writer.println("        }");

        // Visitor pattern.
        writer.println();
        writer.println("        @Override");
        writer.println("        public <R> R accept(Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + className + "(this);");
        writer.println("        }");

        writer.println("    }");
        writer.println();
    }
}
