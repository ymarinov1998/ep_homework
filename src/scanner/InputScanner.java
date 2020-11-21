package scanner;

public interface InputScanner {
    char getCurrentCharacter();
    int getCurrentLine();
    int getCurrentPosition();
    void readNextCharacter();
    boolean hasInput();
}
