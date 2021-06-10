package pl.afyaan;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AFYaan
 * @created 10.06.2021
 * @project Zadanie4
 */
public class App {
    private List<Instruction> instructions;
    private StringBuilder sb;

    public App(String instrPath) throws IOException {
        setInstructions(instrPath);
        sb = new StringBuilder();

        buildResult();

        System.out.println("Długość napisu: " + sb.length());
        System.out.println(sb.toString());

        this.instructions.stream().filter(s -> s.getType() == Type.DOPISZ);


    }

    private void buildResult(){
        instructions.forEach(instruction ->{
            Type type = instruction.getType();
            Object value = instruction.getValue();

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
                    if(index != -1)
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
        return -1;
    }

    private char getNextChar(char value){
        if(value == 'Z') {
            System.out.println("Test z: " + value);
            return 'A';
        }
        else {
            System.out.println("Test: " + value);
            return (char)(value + 1);
        }
    }

    private void setInstructions(String instrPath) throws IOException {
        this.instructions = new ArrayList<>();
        List<String> stringInstr = Files.readAllLines(Paths.get(instrPath), StandardCharsets.UTF_8);


        for(String s : stringInstr){
            this.instructions.add(new Instruction(Type.valueOf(s.split(" ")[0]), s.split(" ")[1]));
        }

    }
}