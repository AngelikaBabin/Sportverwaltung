/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author chris
 */
public class AccountNotFoundException extends Exception{
    private String errMessage;
    public AccountNotFoundException(String errMessage){
        super(errMessage);
        this.errMessage = errMessage;
    }
    
    public AccountNotFoundException(){
        super();
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
