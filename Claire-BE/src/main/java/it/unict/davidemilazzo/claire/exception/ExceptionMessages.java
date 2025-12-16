package it.unict.davidemilazzo.claire.exception;

public final class ExceptionMessages {

    private ExceptionMessages() {}

    public static final String GENERIC_INTERNAL_SERVER_ERROR = "Internal server error: ";

    public static final String EMAIL_ALREADY_REGISTERED = "Email already registered";
    public static final String USERNAME_ALREADY_REGISTERED = "Username already registered";
    public static final String EMAIL_NOT_FOUND = "This email is not registered";

    public static final String AUTHENTICATION_ERROR = "Authentication error, wrong username or password";

    public static final String ERROR_ON_FILE_READ = "Error reading file content data";
    public static final String FILE_IS_EMPTY = "File data (content) is empty";

    public static final String CODE_SNIPPET_IS_EMPTY = "Code snippet is empty";

    public static final String ID_NOT_FOUND = "ID not found";

    public static final String NOT_THE_OWNER = "You are not the owner";

    public static final String MISSING_ANALYSIS_SOURCE_REFERENCE = "Analysis must have either a File or a Snippet";
    public static final String MISSING_ANALYSIS_AI_ID_REFERENCE = "Analysis must have an ai id";

    public static final String MODEL_SYNC_FAILED = "Failed to synchronize AI models with Ollama";

    public static final String MODEL_NOT_FOUND = "This model was not found";
    public static final String INVALID_MODEL_PARAMETER_SIZE = "Invalid model parameter size";
    public static final String AI_PROVIDER_NOT_FOUND = "AI provider not found";

    public static final String JSON_PARSE_ERROR = "Error parsing the received result to a JSON";

    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String JSON_SCHEMA_NOT_FOUND = "JSON schema not found";

    public static final String INVALID_DATA = "Your request contains invalid data";
}
