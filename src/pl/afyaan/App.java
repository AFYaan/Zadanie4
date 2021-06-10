package pl.afyaan;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author AFYaan
 * @created 10.06.2021
 * @project Zadanie4
 */
public class App {
    private List<String> instructions;
    private StringBuilder sb;

    public App(String instrPath) throws IOException {
        setInstructions(instrPath);
        sb = new StringBuilder();

        buildResult();

        System.out.println("Instrukcje:");
        instructions.forEach(System.out::println);

        System.out.println("\nWynik:\n" + sb.toString());
    }

    private void buildResult(){
        instructions.forEach(s ->{
            Type type = Type.valueOf(s.split(" ")[0]);
            Object value = s.split(" ")[1];

            switch (type)
            {
                case DOPISZ:
                    sb.append(value);
                    break;
                case ZMIEN:
                    sb.replace(sb.length() - 1, sb.length(), String.valueOf(value));
                    break;
                case USUN:
                    sb.delete(sb.length() - 1, sb.length());
                    break;
                case PRZESUN:
                    int index = getCharIndex(sb, String.valueOf(value).charAt(0));
                    sb.replace(index , index + 1, String.valueOf(getNextChar(sb.charAt(index))));
                    break;
            }
        });
    }

    private int getCharIndex(StringBuilder sb, char value){
        for(int i = 0; i < sb.length(); i++){
            if(sb.charAt(i) == value){
                return i;
            }
        }
        return 0;
    }

    private char getNextChar(char value){
        if(value == 'Z') return 'A';

        return (char)(value + 1);
    }

    private void setInstructions(String instrPath) throws IOException {
        this.instructions = Files.readAllLines(Paths.get(instrPath), StandardCharsets.UTF_8);
    }
}