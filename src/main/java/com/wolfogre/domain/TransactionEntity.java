package com.wolfogre.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

/**
 * Created by wolfogre on 10/16/16.
 */
@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    private String refno;
    private String stuempno;
    private String transdate;
    private Time transtime;
    private String cardbefbal;
    private String cardaftbal;

    public TransactionEntity() {
    }

    public TransactionEntity(String refno, String stuempno, String transdate, Time transtime, String cardbefbal, String cardaftbal) {
        this.refno = refno;
        this.stuempno = stuempno;
        this.transdate = transdate;
        this.transtime = transtime;
        this.cardbefbal = cardbefbal;
        this.cardaftbal = cardaftbal;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getStuempno() {
        return stuempno;
    }

    public void setStuempno(String stuempno) {
        this.stuempno = stuempno;
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    public Time getTranstime() {
        return transtime;
    }

    public void setTranstime(Time transtime) {
        this.transtime = transtime;
    }

    public String getCardbefbal() {
        return cardbefbal;
    }

    public void setCardbefbal(String cardbefbal) {
        this.cardbefbal = cardbefbal;
    }

    public String getCardaftbal() {
        return cardaftbal;
    }

    public void setCardaftbal(String cardaftbal) {
        this.cardaftbal = cardaftbal;
    }
}
