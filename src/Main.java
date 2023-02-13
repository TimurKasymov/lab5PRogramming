import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.err.close();
        System.setProperty("javax.xml.bind.context.factory", "com.sun.xml.bind.v2.ContextFactory");
        var collecation = new CollectionManager(new ManualContainer(), new UsedCommandsContainer());
        var commandManager = new CommandManager(collecation);
        commandManager.interactiveMod();
        collecation.save();
    }
}