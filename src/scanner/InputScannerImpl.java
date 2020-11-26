package scanner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class InputScannerImpl implements InputScanner {

    private char currentCharacter;
    private int currentPosition = -1;
    private int currentLine = 0;
    private String line;
    private boolean isDoneReading = false;
    private final Scanner scanner;

    public InputScannerImpl(String filePath) throws IOException {
        scanner = new Scanner(new File(filePath));
        readLine();
        readNextCharacter();
    }

    @Override
    public char getCurrentCharacter() {
        return currentCharacter;
    }

    @Override
    public int getCurrentLine() {
        return currentLine;
    }

    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void readNextCharacter() {
        currentPosition++;
        if (currentPosition >= line.length()) {
            currentPosition = 0;
            if (!scanner.hasNextLine()) {
                isDoneReading = true;
                currentCharacter = '\uffff';
                return;
            }
            else {
                readLine();
            }
        }
        currentCharacter = line.charAt(currentPosition);
    }

    public boolean hasInput() {
        return !isDoneReading;
    }

    private void readLine() {
        line = scanner.nextLine();
        currentLine++;
        if (line.isEmpty())
            readLine();
    }
}
