package scanner;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputScannerImpl implements InputScanner {

    private char currentCharacter;
    private int currentPosition = -1;
    private int currentLine = 0;
    private String line = "";
    private boolean isDoneReading = false;
    private final Scanner scanner;

    public InputScannerImpl(String filePath) throws IOException {
        scanner = new Scanner(new File(filePath));
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
            var newLine = getNextLine();
            if (newLine != null) {
                line = newLine;
            } else {
                isDoneReading = true;
                currentCharacter = '\uffff';
                return;
            }
        }
        currentCharacter = line.charAt(currentPosition);
    }

    @Override
    public boolean hasInput() {
        return !isDoneReading;
    }

    private String getNextLine() {
        if (scanner.hasNextLine()) {
            try {
                currentLine++;
                var nextLine = scanner.nextLine();
                while (nextLine.isEmpty()) {
                    currentLine++;
                    nextLine = scanner.nextLine();
                }
                return nextLine;
            } catch (NoSuchElementException exception) {
                return null;
            }
        }
        return null;
    }

}
