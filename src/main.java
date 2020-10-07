import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String args[]) {
        BufferedReader icreader = null;
        BufferedReader pooltab = null;
        BufferedReader symtab = null;
        List<Integer> LC = new ArrayList<>();
        List<String> mc = new ArrayList<>();
        String currentline,symline;
        int count = 0;
        try {

            pooltab = new BufferedReader(new FileReader("/home/yash/IdeaProjects/Pass1with_Literal&Pool_Table/pooltab.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //            System.out.println("\n\n******\n\nIntermediate Code:\n");
//            while ((currentline = icreader.readLine())!= null)
//                System.out.println(currentline);
//            System.out.println("\n\n******\n\nLiteral Table:\n");
//            while ((currentline = littab.readLine())!=null)
//                System.out.println(currentline);
//            System.out.println("\n\n******\n\nPool Table:\n");
//            while ((currentline = pooltab.readLine())!=null)
//                System.out.println(currentline);
//            System.out.println("\n\n******\n\nSymbol Table:\n");
//            while ((currentline = symtab.readLine())!=null)
//                System.out.println(currentline);

        try {
            icreader = new BufferedReader(new FileReader("/home/yash/IdeaProjects/Pass1with_Literal&Pool_Table/intermediatecode.txt"));
            while ((currentline = icreader.readLine())!=null){
                String[] ic = currentline.split("\t");
                if(!ic[0].contains("AD")){
                    LC.add(Integer.valueOf(ic[1]));
                    String[] temp = ic[0].split("\\)|\\(");
                    String[] op = temp[1].split(",");
//                    if(temp.length>4)
//                        System.out.println(ic[0]);
                    if(temp[3].contains("S")){
                        //System.out.println("\n"+temp[3]);
                        symtab = new BufferedReader(new FileReader("/home/yash/IdeaProjects/Pass1with_Literal&Pool_Table/symtab.txt"));
                        while ((symline = symtab.readLine())!=null){
                            String[] checksym = symline.split("\t");
                            //System.out.println(temp[3]);
                            String[] symic = temp[3].split(",");
                            //System.out.println(checksym[0]+","+symic[1]);
                            if(checksym[0].equals(symic[1])){
                                String addinmc = ic[1] + ")" + "\t" + op[1] + "\t" + symic[1] +"\t"+ checksym[2];
                                mc.add(addinmc);
                                count++;
                            }
                        }
                    }else if(isNumeric(temp[3])){                                   //This is a check to verify if there is a Register in one of the operands
                        String[] newtemp = ic[0].split("\\(|\\)" );
                        //System.out.println(newtemp[5]);
                        if(newtemp[5].contains("S")){
                            try {
                                symtab = new BufferedReader(new FileReader("/home/yash/IdeaProjects/Pass1with_Literal&Pool_Table/symtab.txt"));
                                while ((symline = symtab.readLine())!=null){
                                    String[] checksym = symline.split("\t");
                                    //System.out.println(newtemp[5]);
                                    String[] symic = newtemp[5].split(",");
                                    if(checksym[0].equals(symic[1])){
                                        String addinmc = ic[1] + ")" + "\t" + op[1] + "\t" + temp[3] +","+symic[1]+"\t"+ checksym[2];
                                        mc.add(addinmc);
                                        count++;
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            }else if(newtemp[5].contains("L")){
                            int memaddrs = processliteral(newtemp[5]);                                    //Literals go for processing
                            if(memaddrs != 0){
                                String[] opcode = newtemp[1].split(",");
                                String addinmc = ic[1] + ")" + "\t" + opcode[1] +"\t"+temp[3]+ "\t" + memaddrs;
                                mc.add(addinmc);
                                count++;
                            }else {
                                System.out.println("Literal not found in Literal Table. Please check.");
                                System.exit(0);
                            }
                        }
                    }else if (temp[1].contains("DL")){
                        String[] dl = temp[1].split(",");
                        String[] cdl = temp[3].split(",");
                        if(dl[1].equals("0")){          //DC
                            String addinmc = ic[1]+")"+"\t"+/*instead of opcode*/"0"+"\t"+"0"+"\t"+cdl[1];
                            mc.add(addinmc);
                            count++;
                        }else {                         //DS
                            String addinmc = ic[1]+")"+"\t"+dl[1]+"\t"+"0"+"\t"+cdl[1];
                            mc.add(addinmc);
                            count++;
                        }
                    }
                }
            }
            System.out.println("\nProcessing Machine code...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("\n\n***Machine Code***\n"+"LC"+"\t"+"Mnemon.OpC"+"\t"+"Operand OpC"+"\t"+"addrs/val");
            for (int i=0; i< mc.size(); i++)
                System.out.println(mc.get(i));
            FileWriter mcwriter = new FileWriter("machinecode.txt");
            for (String str : mc) {
                mcwriter.write(str + System.lineSeparator());
            }
            mcwriter.close();
            System.out.println("\n\n***Machine Code is generated with file 'machinecode.txt' in root directory.***");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static int processliteral(String newtemp){
        BufferedReader littab = null;
        BufferedReader icreader = null;
        String litline;
        try {
            littab = new BufferedReader(new FileReader("/home/yash/IdeaProjects/Pass1with_Literal&Pool_Table/littab.txt"));
            icreader = new BufferedReader(new FileReader("/home/yash/IdeaProjects/Pass1with_Literal&Pool_Table/intermediatecode.txt"));
            String currentline;
            while ((currentline = icreader.readLine())!=null){
                String[] littemp = currentline.split("\t");
                String[] littemp2 = littemp[0].split("\\(|\\)");
                //System.out.println(littemp2[1]);
                if(littemp2[1].equals("AD,1")){                    //If processing needs to be done at LTORG, add OR condition with littemp2[1].equals("AD,2")
                    String[] passed = newtemp.split(",");
                    while ((litline = littab.readLine())!=null){
                        String[] litsplit = litline.split("\t");
                        if(litsplit[1].contains(passed[1])){
                            return Integer.parseInt(littemp[1]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
