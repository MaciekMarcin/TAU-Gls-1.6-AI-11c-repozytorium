package pl.s15778.tau.candle.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.s15778.tau.candle.domain.CandleStick;
import pl.s15778.tau.candle.domain.Candle;

@Component
@Transactional
public class CandleManagerHibernateImpl implements CandleManager {

    @Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
    }

	@Override
	public Long addCandleStick(CandleStick candleStick) {
        if (candleStick.getId() != null) throw new IllegalArgumentException("the candleStick ID should be null if added to database");
		sessionFactory.getCurrentSession().persist(candleStick);
		for (Candle candle : candleStick.getCandles()) {
			candle.setCandleStick(candleStick);
			sessionFactory.getCurrentSession().update(candle);
		}
		sessionFactory.getCurrentSession().flush();
		return candleStick.getId();
	}

	@Override
    public void updateCandleStick(CandleStick candleStick) {
        sessionFactory.getCurrentSession().update(candleStick);
    }

    @Override
	public CandleStick findCandleStickById(Long id) {
		CandleStick c = (CandleStick) sessionFactory
		.getCurrentSession()
		.get(CandleStick.class, id);
		return c;
	}

	@Override
	public CandleStick findCandleStickByFragment(String fragmentStick) {
		return (CandleStick) sessionFactory.getCurrentSession().get(CandleStick.class, fragmentStick);
	}

	@Override
	public void deleteCandleStick(CandleStick candleStick) {
		candleStick = (CandleStick) sessionFactory.getCurrentSession().get(CandleStick.class,
				candleStick.getId());
		sessionFactory.getCurrentSession().delete(candleStick);
	}

	@Override
	public List<CandleStick> findAllCandleStick() {
		return sessionFactory.getCurrentSession().getNamedQuery("candleStick.all")
				.list();
	}

	@Override
	public List<CandleStick> findCandleStick(String nameStickFragment) {
		return (List<CandleStick>) sessionFactory.getCurrentSession()
				.getNamedQuery("candleStick.findCandleStick")
				.setString("nameStickFragment", "%"+nameStickFragment+"%")
				.list();
	}

    @Override
	public Long addCandle(Candle candle) {
		/*
        if (candle.getId() != null) throw new IllegalArgumentException("the candle ID should be null if added to database");
		sessionFactory.getCurrentSession().persist(candle);
		sessionFactory.getCurrentSession().flush();
		return candle.getId();
		*/
		return (Long) sessionFactory.getCurrentSession().save(candle);
	}

	@Override
    public void updateCandle(Candle candle) {
        sessionFactory.getCurrentSession().update(candle);
    }

    @Override
	public Candle findCandleById(Long id) {
		return (Candle) sessionFactory.getCurrentSession().get(Candle.class, id);
	}

	@Override
	public void deleteCandle(Candle candle) {
		candle = (Candle) sessionFactory.getCurrentSession().get(Candle.class,
				candle.getId());
		sessionFactory.getCurrentSession().delete(candle);
	}

	@Override
	public List<Candle> findAllCandles() {
		return sessionFactory.getCurrentSession().getNamedQuery("candle.all")
				.list();
	}

	@Override
	public List<Candle> getCandlesOfCandleStick(Long id) {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("candle.allForCandleStick")
				.setLong("id", id).list();
	}
	
	@Override
	public void moveCandles(CandleStick candleStick1, CandleStick candleStick2, Candle candle) {
		candleStick2.removeCandle(candle);
		candleStick1.addCandle(candle);
		updateCandleStick(candleStick1);
		updateCandleStick(candleStick2);
	}

}