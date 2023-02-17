import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.err.close();
        System.setProperty("javax.xml.bind.context.factory", "com.sun.xml.bind.v2.ContextFactory");
        var collecation = new CollectionManager(new FileHandler());
        var commandManager = new CommandManager(collecation);
        for(;;){
            System.out.print("Enter a command: ");
            var scanner = new Scanner(System.in);
            commandManager.executeCommand(scanner.nextLine());
        }
    }
}