package uk.co.unclealex.rokta.server.model;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import uk.co.unclealex.hibernate.model.KeyedBean;

@Entity
@XmlRootElement(name="game")
@XmlType
public class Game extends KeyedBean<Game> {

	private Person i_instigator;
	private SortedSet<Round> i_rounds;
	private Date i_datePlayed;
	private Integer i_yearPlayed;
	private Integer i_monthPlayed;
	private Integer i_weekPlayed;
	private Integer i_dayPlayed;
	
	@Transient
	@XmlTransient
	public SortedSet<Person> getParticipants() {
		if (getRounds() == null || getRounds().isEmpty()) {
			return new TreeSet<Person>();
		}
		Round firstRound = getRounds().first();
		return firstRound.getParticipants();
	}
	
	@Transient
	@XmlTransient
	public Person getLoser() {
		Person loser = null;
		
		if (getRounds() != null && !getRounds().isEmpty()) {
			Round finalRound = getRounds().last();
			Set<Person> losers = finalRound.getLosers();
			if (losers.size() == 1) {
				loser = losers.iterator().next();
			}
		}
		return loser;
		
	}
	
	@Column(nullable=false, unique=true)
	@XmlElement(name="date-played")
	@Index(name="datePlayed")
	public Date getDatePlayed() {
		return i_datePlayed;
	}
	
	public void setDatePlayed(Date datePlayed) {
		i_datePlayed = datePlayed;
	}
	
	@ManyToOne
	@XmlIDREF
	public Person getInstigator() {
		return i_instigator;
	}
	
	public void setInstigator(Person instigator) {
		i_instigator = instigator;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="game_id")
	@Sort(type = SortType.NATURAL)
	@XmlElementWrapper(name="rounds")
	@XmlElement(name="round")
	@Fetch(FetchMode.JOIN)
	public SortedSet<Round> getRounds() {
		return i_rounds;
	}
	
	public void setRounds(SortedSet<Round> rounds) {
		i_rounds = rounds;
	}
	
	@Override
	@Id @GeneratedValue
	public Integer getId() {
		return super.getId();
	}

	@Override
	public int compareTo(Game o) {
		Integer cmp = getDatePlayed().compareTo(o.getDatePlayed());
		return cmp!=0?cmp:super.compareTo(o);
	}
	
	@Override
	public String toString() {
		return "[Game " + getId() + " @ " + getDatePlayed() + "]";
	}

	@Index(name="yearPlayed")
	public Integer getYearPlayed() {
		return i_yearPlayed;
	}

	public void setYearPlayed(Integer yearPlayed) {
		i_yearPlayed = yearPlayed;
	}

	@Index(name="weekPlayed")
	public Integer getWeekPlayed() {
		return i_weekPlayed;
	}

	public void setWeekPlayed(Integer weekPlayed) {
		i_weekPlayed = weekPlayed;
	}

	@Index(name="dayPlayed")
	public Integer getDayPlayed() {
		return i_dayPlayed;
	}

	public void setDayPlayed(Integer dayPlayed) {
		i_dayPlayed = dayPlayed;
	}

	@Index(name="monthPlayed")
	public Integer getMonthPlayed() {
		return i_monthPlayed;
	}

	public void setMonthPlayed(Integer monthPlayed) {
		i_monthPlayed = monthPlayed;
	}
}
