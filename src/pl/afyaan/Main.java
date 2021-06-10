package pl.afyaan;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Main {
    private static String instrPath = "C:\\Users\\afyaa\\IdeaProjects\\Zadanie4\\instrukcje.txt";

    public static void main(String[] args) throws IOException {
        new App(instrPath);
    }
}
