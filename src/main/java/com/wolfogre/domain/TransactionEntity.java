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
    private String transdate;
    private Time transtime;

    public TransactionEntity() {
    }

    public TransactionEntity(String refno, String transdate, Time transtime) {
        this.refno = refno;
        this.transdate = transdate;
        this.transtime = transtime;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
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
}
