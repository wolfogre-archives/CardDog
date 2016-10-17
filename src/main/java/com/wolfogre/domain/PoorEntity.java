package com.wolfogre.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by wolfogre on 10/17/16.
 */
@Entity
@Table(name = "poor")
public class PoorEntity {
    @Id
    private String id;
    private String stuempid;

    public PoorEntity() {
    }

    public PoorEntity(String id, String stuempid) {
        this.id = id;
        this.stuempid = stuempid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStuempid() {
        return stuempid;
    }

    public void setStuempid(String stuempid) {
        this.stuempid = stuempid;
    }
}
