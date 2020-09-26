import java.beans.ExceptionListener;
import java.util.*;
import java.io.*;

public class grab_file {
    static BufferedReader get() {

        try {
            BufferedReader assembley = new BufferedReader(new FileReader("/home/yash/StudioProjects/IDEA Projects/src/code.txt"));
            return (assembley);
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
public static void main(String[] args) {
        int c;
    new parse().display_assembly();
    new parse().storeassembly();
    }
}