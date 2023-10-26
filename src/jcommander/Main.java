package jcommander;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new CommanderInstance();
        } catch (IOException e) {
            System.err.println("The application has stopped unexpectedly.");
        }
    }
}