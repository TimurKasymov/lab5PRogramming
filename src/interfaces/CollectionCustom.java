package interfaces;

import java.time.LocalDateTime;
import java.util.LinkedList;

public interface CollectionCustom<TEntity> {
    public boolean validateData();
    LinkedList<TEntity> get();
    LocalDateTime getInitializationTime();
    public Class getElementType();
    void save();
}
