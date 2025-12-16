package it.unict.davidemilazzo.claire.util;

import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;

import java.util.Map;
import java.util.Optional;

public class ProgrammingLanguageUtils {

    private static final Map<String, ProgrammingLanguage> EXTENSION_MAP = Map.ofEntries(
            // Java
            Map.entry("java", ProgrammingLanguage.JAVA),

            // C
            Map.entry("c", ProgrammingLanguage.C),
            Map.entry("h", ProgrammingLanguage.C),  // header file

            // C++
            Map.entry("cpp", ProgrammingLanguage.CPP),
            Map.entry("cxx", ProgrammingLanguage.CPP),
            Map.entry("cc", ProgrammingLanguage.CPP),
            Map.entry("c++", ProgrammingLanguage.CPP),
            Map.entry("hpp", ProgrammingLanguage.CPP), // header

            // C#
            Map.entry("cs", ProgrammingLanguage.CSHARP),

            // Python
            Map.entry("py", ProgrammingLanguage.PYTHON),
            Map.entry("pyw", ProgrammingLanguage.PYTHON),
            Map.entry("python", ProgrammingLanguage.PYTHON),

            // JavaScript / TypeScript
            Map.entry("js", ProgrammingLanguage.JAVASCRIPT),
            Map.entry("mjs", ProgrammingLanguage.JAVASCRIPT),
            Map.entry("cjs", ProgrammingLanguage.JAVASCRIPT),
            Map.entry("ts", ProgrammingLanguage.TYPESCRIPT),

            // Ruby
            Map.entry("rb", ProgrammingLanguage.RUBY),

            // PHP
            Map.entry("php", ProgrammingLanguage.PHP),

            // Swift
            Map.entry("swift", ProgrammingLanguage.SWIFT),

            // Kotlin
            Map.entry("kt", ProgrammingLanguage.KOTLIN),

            // Go
            Map.entry("go", ProgrammingLanguage.GO),

            // Rust
            Map.entry("rs", ProgrammingLanguage.RUST),

            // Scala
            Map.entry("scala", ProgrammingLanguage.SCALA),

            // Dart
            Map.entry("dart", ProgrammingLanguage.DART),

            // Perl
            Map.entry("pl", ProgrammingLanguage.PERL),
            Map.entry("pm", ProgrammingLanguage.PERL),

            // Haskell
            Map.entry("hs", ProgrammingLanguage.HASKELL),

            // Lua
            Map.entry("lua", ProgrammingLanguage.LUA),

            // R
            Map.entry("r", ProgrammingLanguage.R),

            // Objective-C
            Map.entry("m", ProgrammingLanguage.OBJECTIVE_C),
            Map.entry("mm", ProgrammingLanguage.OBJECTIVE_C),

            // MATLAB
            Map.entry("matlab", ProgrammingLanguage.MATLAB),

            // Groovy
            Map.entry("groovy", ProgrammingLanguage.GROOVY),

            // Julia
            Map.entry("jl", ProgrammingLanguage.JULIA),

            // Shell
            Map.entry("sh", ProgrammingLanguage.SHELL),
            Map.entry("bash", ProgrammingLanguage.SHELL),
            Map.entry("zsh", ProgrammingLanguage.SHELL),

            // SQL
            Map.entry("sql", ProgrammingLanguage.SQL),

            // Pseudo-code
            Map.entry("pseudo", ProgrammingLanguage.PSEUDO_CODE),
            Map.entry("pseudocode", ProgrammingLanguage.PSEUDO_CODE)
    );

    public static Optional<ProgrammingLanguage> fromExtension(String extension) {
        if (extension == null || extension.isBlank()) return Optional.empty();
        String ext = extension.trim().toLowerCase();
        return Optional.ofNullable(EXTENSION_MAP.get(ext));
    }
}
