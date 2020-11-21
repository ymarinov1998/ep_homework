package scanner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ScannerImpl implements InputScanner {

    private char currentCharacter;
    private int currentPosition = -1;
    private int currentLine = 0;
    private String line;
    private boolean hasInput = true;
    private Scanner scanner;

    public ScannerImpl(String filePath) throws IOException {
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
            readLine();
        }
        currentCharacter = line.charAt(currentPosition);
    }

    public boolean hasInput() {
        return hasInput;
    }

    private void readLine() {
        if (!scanner.hasNextLine()) {
            hasInput = false;
            return;
        }
        line = scanner.nextLine();
        if (line.isEmpty())
            readLine();
    }
}
