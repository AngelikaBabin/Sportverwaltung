/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chris
 */
@XmlRootElement
public class AccountAlreadyExistsException extends Exception{
    private String errMessage;
    public AccountAlreadyExistsException(String errMessage){
        super(errMessage);
        this.errMessage = errMessage;
    }
    
    public AccountAlreadyExistsException(){
        super();
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
    
    
}
