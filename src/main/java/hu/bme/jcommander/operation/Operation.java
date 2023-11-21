package hu.bme.jcommander.operation;

public abstract class Operation implements Runnable {
    protected volatile boolean failed = false;

    public boolean isFailed() {
        return failed;
    }

    public Operation then(Operation operation) {
        return new ThenOperation(this, operation);
    }
}
