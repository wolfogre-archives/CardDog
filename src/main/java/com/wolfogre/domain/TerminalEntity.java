package com.wolfogre.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by wolfogre on 11/20/16.
 */
@Entity
@Table(name = "terminal")
public class TerminalEntity {
    @Id
    @Column(name = "termid")
    private String termId;
    @Column(name = "termname")
    private String termName;
    @Column(name = "businessid")
    private Integer businessId;
    @Column(name = "businessname")
    private String businessName;

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TerminalEntity that = (TerminalEntity) o;

        if (termId != null ? !termId.equals(that.termId) : that.termId != null) return false;
        if (termName != null ? !termName.equals(that.termName) : that.termName != null) return false;
        if (businessId != null ? !businessId.equals(that.businessId) : that.businessId != null) return false;
        return businessName != null ? businessName.equals(that.businessName) : that.businessName == null;

    }

    @Override
    public int hashCode() {
        int result = termId != null ? termId.hashCode() : 0;
        result = 31 * result + (termName != null ? termName.hashCode() : 0);
        result = 31 * result + (businessId != null ? businessId.hashCode() : 0);
        result = 31 * result + (businessName != null ? businessName.hashCode() : 0);
        return result;
    }
}
