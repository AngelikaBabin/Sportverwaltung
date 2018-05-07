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
public class FilterExcpetion extends Exception{
    private String errMessage;
    public FilterExcpetion(String errMessage){
        super(errMessage);
        this.errMessage = errMessage;
    }
    
    public FilterExcpetion(){
        super();
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
