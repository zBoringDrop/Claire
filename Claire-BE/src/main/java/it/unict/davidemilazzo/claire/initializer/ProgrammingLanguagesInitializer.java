package it.unict.davidemilazzo.claire.initializer;

import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguageEntity;
import it.unict.davidemilazzo.claire.respository.ProgrammingLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProgrammingLanguagesInitializer implements CommandLineRunner {

    private final ProgrammingLanguageRepository programmingLanguageRepository;
    private static final Logger log = LogManager.getLogger(ProgrammingLanguagesInitializer.class);

    private final Set<ProgrammingLanguageEntity> programmingLanguageSet = new HashSet<>(List.of(
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.JAVA.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.C.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.CPP.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.CSHARP.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.PYTHON.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.JAVASCRIPT.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.TYPESCRIPT.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.RUBY.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.PHP.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.SWIFT.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.KOTLIN.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.GO.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.RUST.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.SCALA.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.DART.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.PERL.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.HASKELL.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.LUA.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.R.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.OBJECTIVE_C.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.MATLAB.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.GROOVY.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.JULIA.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.SHELL.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.SQL.name()),
            new ProgrammingLanguageEntity(null, ProgrammingLanguage.PSEUDO_CODE.name())
    ));

    @Override
    public void run(String... args) throws Exception {
        log.info("Updating 'programming_language' table with default values...");
        for (ProgrammingLanguageEntity programmingLanguage : programmingLanguageSet) {
            if (!programmingLanguageRepository.existsByName(programmingLanguage.getName())) {
                programmingLanguageRepository.save(programmingLanguage);
            }
        }
        log.info("Table 'programming_language' updated successfully");
    }
}
