/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Kraschl
 */
public class FilterException extends Exception{
    private String errMessage;
    public FilterException(String errMessage){
        super(errMessage);
        this.errMessage = errMessage;
    }
    
    public FilterException(){
        super();
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
