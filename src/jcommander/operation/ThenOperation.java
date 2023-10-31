package jcommander.operation;

public class ThenOperation extends Operation {

    private final Operation operation;
    private final Operation then;

    // It has package visibility, as it is discouraged to use this directly.
    // Instead, Operation.then(...) should be used.
    ThenOperation(Operation operation, Operation then) {
        this.operation = operation;
        this.then = then;
    }

    @Override
    public void run() {
        operation.run();

        if (operation.isFailed()) {
            failed = true;
            return;
        }

        then.run();
        failed = then.isFailed();
    }
}
