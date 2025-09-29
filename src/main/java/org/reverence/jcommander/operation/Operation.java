package org.reverence.jcommander.operation;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
/**
 * Represents an abstract operation that can be executed in the application.
 */
public abstract class Operation implements Runnable {
    protected volatile boolean failed = false;

    /**
     * Checks if the operation has failed.
     *
     * @return true if the operation has failed, false otherwise
     */
    public boolean isFailed() {
        return failed;
    }

    /**
     * Chains the current operation with another operation to be executed sequentially.
     *
     * @param operation the operation to be executed after the current one
     * @return a new Operation representing the sequential execution of the current and the provided operations
     */
    public Operation then(Operation operation) {
        return new ThenOperation(this, operation);
    }
}
