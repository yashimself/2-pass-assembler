import java.io.*;

public class grab_file {
    static BufferedReader get() {

        try {
            BufferedReader assembley = new BufferedReader(new FileReader("/home/yash/IdeaProjects/Pass1with_Literal&Pool_Table/src/code.txt"));
            return (assembley);
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
public static void main(String[] args) {
    new parse().display_assembly();
    new parse().storeassembly();
    }
}