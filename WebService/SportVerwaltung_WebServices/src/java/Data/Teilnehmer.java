/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kraschl
 */
@XmlRootElement
public class Teilnehmer extends Account {
    private double score;

    public Teilnehmer(int id, String name, String email, String password, double score) {
        super(id, name, email, password);
        this.score = score;
    }
    
    public Teilnehmer(String email){
        super(email);
    }
    
    public Teilnehmer(){
        super();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Teilnehmer{" + "score=" + score + '}';
    }
}
