package pl.s15778.tau.candle.domain;

import javax.persistence.*;

@Entity(name = "Candle")
@Table(name = "candle")
@NamedQueries({
        @NamedQuery(name = "candle.all", query = "Select p from Candle p"),
        @NamedQuery(name = "candle.findCandles", query = "Select c from Candle c where c.name like :nameFragment")
})

public class Candle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public String company;
    public Integer cbt;

    public Candle() {

    }

    public Candle(String name, String company, Integer cbt) {
        this.id = null;
        this.name = name;
        this.company = company;
        this.cbt = cbt;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public void setCbt(int cbt) {
        this.cbt = cbt;
    }

    public Integer getCbt() {
        return cbt;
    }

    @Override
    public boolean equals(Object o) {
        Candle other = (Candle) o;
        boolean ret = other.getName().equals(this.getName()) && other.getCompany().equals(this.getCompany()) && ((other.getId() == this.getId()) || (other.getId().longValue() == this.getId().longValue())) && ((other.getCbt() == this.getCbt()) || (other.getCbt().intValue() == this.getCbt().intValue()));
    return ret;
    }

    @Override
    public String toString() {
        return "[" + id + ", " + name + ", " + company + ", " + cbt + "]";
    }
}