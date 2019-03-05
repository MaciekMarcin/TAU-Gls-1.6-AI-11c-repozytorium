package pl.s15778.tau.candle.domain;


public class Candle 
{
    long id;
    String name;
    String company;
    long burningTime;

    public void setId(long i) {
        id = i;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setBurningTime(long t) {
        burningTime = t;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public long getBurningTime() {
        return burningTime;
    }
}
