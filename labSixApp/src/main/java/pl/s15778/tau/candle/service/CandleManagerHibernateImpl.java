package pl.s15778.tau.candle.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	public Long addCandle(Candle candle) {
        if (candle.getId() != null) throw new IllegalArgumentException("the candle ID should be null if added to database");
		sessionFactory.getCurrentSession().persist(candle);
		sessionFactory.getCurrentSession().flush();
		return candle.getId();
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
	public Candle findCandleByFragment(String fragment) {
		return (Candle) sessionFactory.getCurrentSession().get(Candle.class, fragment);
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
	public List<Candle> findCandles(String nameFragment) {
		return (List<Candle>) sessionFactory.getCurrentSession()
				.getNamedQuery("candle.findCandles")
				.setString("nameFragment", "%"+nameFragment+"%")
				.list();
	}

}