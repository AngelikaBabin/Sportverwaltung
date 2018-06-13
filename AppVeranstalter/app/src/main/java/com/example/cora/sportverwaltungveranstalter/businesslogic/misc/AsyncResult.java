package com.example.cora.sportverwaltungveranstalter.businesslogic.misc;


/**
 * @babin
 */
public class AsyncResult<T> {
    private T result;
    private Exception err;

    public T getResult(){
        return result;
    }

    public Exception getError(){return err;}

    public AsyncResult(T result){
        super();
        this.result = result;
    }

    public AsyncResult(Exception err){
        super();
        this.err = err;
    }
}
