/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chris
 */
@XmlRootElement
public class Teilnehmer {
    private int id;
    private double score;

    public Teilnehmer(int id, double score) {
        this.id = id;
        this.score = score;
    }
    
    public Teilnehmer(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Teilnehmer{" + "id=" + id + ", score=" + score + '}';
    }
}
