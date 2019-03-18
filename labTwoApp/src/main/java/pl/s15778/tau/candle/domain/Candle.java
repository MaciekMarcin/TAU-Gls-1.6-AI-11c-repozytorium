package pl.s15778.tau.candle.domain;


public class Candle {
    private Long id;
    private String name;
    private String company;
    private Integer cbt;
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

    public Integer getCbt() {
        return cbt;
    }

    public void setCbt(int cbt) {
        this.cbt = cbt;
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
