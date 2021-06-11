package pl.afyaan;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App {
    private List<Instruction> instructions;
    private StringBuilder sb;

    public App(String instrPath) throws IOException {
        setInstructions(instrPath);
        sb = new StringBuilder();

        buildResult();

        //Zad 4.1
        System.out.println("Zadanie 4.1\n  " +
                "Długość napisu: " + sb.length());
        //Zad 4.2
        zad42();

        //Zad 4.3
        zad43();

        //Zad4.4
        System.out.println("\nZadanie 4.4\n  " +
                "Napis powstały po wykonaniu wszystkich instrukcji to: " + sb.toString());
    }

    private void zad42(){
        Map<Type, Integer> map = new HashMap<>();
        Type preType = null;
        int count = 0;

        for(Instruction instr : instructions){
            Type currentType = instr.getType();

            if(preType == null){
                preType = currentType;
                count = -1;
            }
            count++;
            if(preType != currentType){
                if(map.get(preType) == null || map.get(preType) < count){
                    map.put(preType, count);
                }
                count = 0;
            }
            preType = currentType;
        }

        if(map.get(preType) == null || map.get(preType) < count){
            map.put(preType, count+1);
        }

        map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(v -> {
                    System.out.println("\nZadanie 4.2:\n  " +
                            "Najdłuższy ciąg występujących kolejno po sobie instrukcji tego samego rodzaju to:\n" +
                            "Rodzaj instrukcji [" + v.getKey().toString() + "] wystąpiła " + v.getValue()
                            + (v.getValue() == 1 ? " raz" : " razy"));
                });
    }

    private void zad43(){
        instructions.stream()
                .filter(s -> s.getType() == Type.DOPISZ)
                .map(s -> String.valueOf(s.getValue()).charAt(0))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(v -> {
                    System.out.println("\nZadanie 4.3\n  " +
                            "Najczęsciej dopisywaną literą jest [" + v.getKey() + "] i występuje " + v.getValue()
                            + (v.getValue() == 1 ? " raz" : " razy"));
                });
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
            return 'A';
        }
        else {
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