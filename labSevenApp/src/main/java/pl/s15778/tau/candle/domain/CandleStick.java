package pl.s15778.tau.candle.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "CandleStick")
@Table(name = "candleStick")
@NamedQueries({
        @NamedQuery(name = "candleStick.all", query = "Select p from CandleStick p"),
        @NamedQuery(name = "candleStick.findCandleStick", query = "Select c from CandleStick c where c.nameStick like :nameStickFragment")
})

public class CandleStick {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String nameStick;
    public Integer space;

    @OneToMany(cascade = CascadeType.PERSIST,
		fetch = FetchType.EAGER,
		orphanRemoval=false,
		mappedBy = "candleStick"
    )
    private List<Candle> candles = new LinkedList<>();

    public CandleStick() {

    }

    public CandleStick(String nameStick, Integer space) {
        this.id = null;
        this.nameStick = nameStick;
        this.space = space;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameStick() {
        return nameStick;
    }

    public void setNameStick(String nameStick) {
        this.nameStick = nameStick;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public Integer getSpace() {
        return space;
    }

    @Override
    public boolean equals(Object o) {
        CandleStick other = (CandleStick) o;
        boolean ret = other.getNameStick().equals(this.getNameStick()) && ((other.getId() == this.getId()) || (other.getId().longValue() == this.getId().longValue())) && ((other.getSpace() == this.getSpace()) || (other.getSpace().intValue() == this.getSpace().intValue()));
        return ret;
    }

    @Override
    public String toString() {
        return "[" + id + ", " + nameStick + ", " + space + "]";
    }

    public List<Candle> getCandles() {
		return candles;
	}

	public void setCandles(List<Candle> candles) {
		this.candles = candles;
	}

	public void addCandle(Candle candle) {
		candles.add(candle);
	}

	public void removeCandle(Candle candle) {
		candles.remove(candle);
    }

}