/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kraschl
 */
@XmlRootElement
public class RegisterExcpetion extends Exception{
    private String errMessage;
    public RegisterExcpetion(String errMessage){
        super(errMessage);
        this.errMessage = errMessage;
    }
    
    public RegisterExcpetion(){
        super();
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
    
    
}
