package ru.javafx.multitenant;

public class MultitenantContext {
    
    private static ThreadLocal<Object> multitenantFlag = new ThreadLocal<>();
    private static ThreadLocal<Object> multitenantId = new ThreadLocal<>();

    public static Object getMultitenantFlag() {
        return multitenantFlag.get();
    }
    
    public static void setMultitenantFlag(Object flag) {
        multitenantFlag.set(flag);
    }

    public static Object getMultitenantId() {
        return multitenantId.get();
    }

    public static void setMultitenantId(Object id) {
        multitenantId.set(id);
    }
    
}
