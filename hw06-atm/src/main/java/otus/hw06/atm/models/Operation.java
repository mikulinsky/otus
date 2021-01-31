package otus.hw06.atm.models;

import otus.hw06.atm.exceptions.IllegalOperationException;

public enum Operation {
    LOGIN, INFO, GET, PUSH, EXIT;

    public static Operation getOperationByName(String operation) throws IllegalOperationException {
        try {
            return Operation.valueOf(operation.toUpperCase());
        } catch (Exception e) {
            throw new IllegalOperationException();
        }
    }
}
