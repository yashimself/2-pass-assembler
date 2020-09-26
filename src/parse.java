import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class parse extends grab_file {
    String[] Mnemonic = {"MOVER", "START", "READ", "ADD", "MOVEM", "PRINT", "DC", "DS", "END"};
    String[] Type = {"IS", "AD", "IS", "IS", "IS", "IS", "DL", "DL", "AD"};
    String[] Registers = {"AREG"};
    public String[] added = new String[10];
    int[] Opcode = {1, 0, 2, 3, 4, 5, 0, 0, 1};
    int[] Size = {2, 0, 2, 2, 2, 2, 0, 0, 0};
    List<String> intermediate = new ArrayList<String>();
    List<String> operand = new ArrayList<String>();
    BufferedReader grabbed_file = get();

    public void display_assembly() {
        try {
            if (grabbed_file.readLine() != null) {
                System.out.println("Read machine code file successfully. Parsing...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeassembly() {
        int LC = 0, i = 0, j = 1, q = 0, k = 0;
        List<Integer> LCarr = new ArrayList<>();
        List<String> Symbols = new ArrayList<>();
        int[] regex = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        String currentline;
        LCarr.add(0);
        try {
            while ((currentline = grabbed_file.readLine()) != null) {
                System.out.println(currentline);
                if (currentline.contains("START ")) {
                    currentline = currentline.replace("START ", "");
                    LC = Integer.parseInt(currentline);
                    //System.out.println(LC);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader grabbed_file = get();
        try {
            while ((currentline = grabbed_file.readLine()) != null) {
                String[] inst = currentline.split(" ");
                for (i = 0; i < 9; i++) {
                    //System.out.println("inst:"+inst[0]+" mneumonic:"+Mnemonic[i]);
                    if (inst[0].equals(Mnemonic[i])) {
                        if (inst[0].equals("START")) {
                            String Startoperand = "(" + Type[i] + "," + Opcode[i] + ")" + "(" + "C" + "," + LC + ")";
                            intermediate.add(Startoperand);
                        } else {
                            if (!(inst[0].equals("START") || inst[0].equals("END"))) {
                                String[] tempstring;
                                if (inst[1].contains(",")) {
                                    tempstring = inst[1].split(",");
                                    if (tempstring[0].equals(Registers[0])) {
                                        if (!tempstring[1].equals(Registers[0])) {
                                            k=doesexist(added,tempstring[1]);
                                            String temp = "(" + Type[i] + "," + Opcode[i] + ")" + "(" + Registers.length + ")" + "(" + "S" + "," + k + ")";
                                            intermediate.add(temp);
                                            if (!(Arrays.stream(added).anyMatch(tempstring[1]::equals))) {
                                                String syminp = q + "\t" + tempstring[1] + "\t" + LC;
                                                Symbols.add(syminp);
                                                added[q] = tempstring[1];
                                                q += 1;
                                            }
                                            LCarr.add(LC);
                                            LC += Size[i];
                                        } else if (tempstring[1].equals(Registers[0])) {
                                            String temp = "(" + Type[i] + "," + Opcode[i] + ")" + "(" + Registers.length + ")" + "(" + Registers.length + ")";
                                            intermediate.add(temp);
                                            LCarr.add(LC);
                                            LC += Size[i];
                                        }
                                    } else if (!isNumeric(tempstring[0])) {
                                        k=doesexist(added,tempstring[1]);
                                        String temp = "(" + Type[i] + "," + Opcode[i] + ")" + "(" + "S" + "," + k + ")";
                                        intermediate.add(temp);
                                        if (!(Arrays.stream(added).anyMatch(tempstring[1]::equals))) {
                                            String syminp = q + "\t" + tempstring[1] + "\t" + LC;
                                            Symbols.add(syminp);
                                            added[q] = tempstring[1];
                                            q += 1;
                                        }
                                        LCarr.add(LC);
                                        LC += Size[i];
                                    } else {
                                        String temp = "(" + Type[i] + "," + Opcode[i] + ")" + "(" + "C" + "," + tempstring[0] + ")";
                                        intermediate.add(temp);
                                        LCarr.add(LC);
                                        LC += Size[i];
                                    }
                                } else {
                                    if (inst[1].equals(Registers[0])) {
                                        String temp = "(" + Type[i] + "," + Opcode[i] + ")" + "(" + Registers.length + ")";
                                        intermediate.add(temp);
                                        LCarr.add(LC);
                                        LC += Size[i];
                                    } else {
                                        k=doesexist(added,inst[1]);
                                        String temp = "(" + Type[i] + "," + Opcode[i] + ")" + "(" + "S" + "," + k + ")";
                                        intermediate.add(temp);
                                        if (!(Arrays.stream(added).anyMatch(inst[1]::equals))) {
                                            String syminp = q + "\t" + inst[1] + "\t" + LC;
                                            Symbols.add(syminp);
                                            added[q] = inst[1];
                                            q += 1;
                                        }
                                        LCarr.add(LC);
                                        LC += Size[i];
                                    }
                                }
                            } else if (inst[0].equals("END")) {
                                String temp = "(" + Type[8] + "," + Opcode[8] + ")";
                                intermediate.add(temp);
                                LCarr.add(LC);
                                LC += Size[8];
                            }
                        }
                    } else if (inst.length > 1 && inst[1].equals(Mnemonic[i])) {
                        if (isNumeric(inst[2])) {
                            String temp = "(" + Type[i] + "," + Opcode[i] + ")" + "(" + "C" + "," + inst[2] + ")";
                            intermediate.add(temp);
                            LCarr.add(LC);
                            if (inst[1].equals("DS"))
                                LC += Integer.parseInt(inst[2]);
                            else {
                                LC += 1;
                            }
                        }

                    }

                }
                if (intermediate.isEmpty()) {
                    System.out.println("Error! Instruction not found in MOT table");
                    System.exit(0);
                }
                j = j + 1;
            }
            System.out.println("\nIntermediate Code:\n");
            for (i = 0; i < intermediate.size(); i++)
                System.out.println(intermediate.get(i) + "\t" + "LC: " + LCarr.get(i));
            FileWriter writer = new FileWriter("intermediatecode.txt");
            for (String str : intermediate) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            System.out.println("\n\t\t***Generated intermediate code file! Check file with filename 'intermediatecode.txt' in Project Directory***\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nSymbol Table:\n");
        for (i = 0; i < Symbols.size(); i++)
            System.out.println(Symbols.get(i));
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int doesexist(String[] added, String chec) {
        int m = 0;
        try {
                for (m = 0; m < added.length; m++) {
                    if ( added[m] != null && added[m].equals(chec)) {
                        return m;
                    }else if (added[m]==null){
                        return m;
                    }
                }
//            return m;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
        }
        return m;
    }
}
