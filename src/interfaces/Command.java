package interfaces;

public interface Command {
    boolean execute(String[] args);
    String getInfo();
}
