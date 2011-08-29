/**
 * 
 */
package uk.co.unclealex.rokta.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import uk.co.unclealex.hibernate.model.KeyedBean;

/**
 * @author alex
 *
 */
@Entity
@NamedQueries(value={
		@NamedQuery(name="colour.getAll", query="from Colour c"),
		@NamedQuery(name="colour.byName", query="from Colour c where c.name = :name"),
		@NamedQuery(name="colour.byHtmlName", query="from Colour c where c.htmlName = :name")
		})
@XmlRootElement(name="colour")
@XmlType(propOrder={"name", "htmlName", "red", "green", "blue"})
public class Colour extends KeyedBean<Colour> {

	private String i_htmlName;
	private String i_name;
	private Short i_red;
	private Short i_green;
	private Short i_blue;

	@Override
	public int compareTo(Colour o) {
		return getName().compareTo(o.getName());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Colour && compareTo((Colour) obj) == 0;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	@Id @GeneratedValue
	public Integer getId() {
		return super.getId();
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Column(unique=true, nullable=false)
	@XmlID
	public String getName() {
		return i_name;
	}

	public void setName(String name) {
		i_name = name;
	}
	
	/**
	 * @return the blue
	 */
	@Column(nullable=false)
	public Short getBlue() {
		return i_blue;
	}

	/**
	 * @param blue the blue to set
	 */
	public void setBlue(Short blue) {
		i_blue = blue;
	}

	/**
	 * @return the green
	 */
	@Column(nullable=false)
	public Short getGreen() {
		return i_green;
	}

	/**
	 * @param green the green to set
	 */
	public void setGreen(Short green) {
		i_green = green;
	}

	/**
	 * @return the htmlName
	 */
	@Column(unique=true, nullable=false)
	@XmlElement(name="html-name")
	public String getHtmlName() {
		return i_htmlName;
	}

	/**
	 * @param htmlName the htmlName to set
	 */
	public void setHtmlName(String htmlName) {
		i_htmlName = htmlName;
	}

	/**
	 * @return the red
	 */
	@Column(nullable=false)
	public Short getRed() {
		return i_red;
	}

	/**
	 * @param red the red to set
	 */
	public void setRed(Short red) {
		i_red = red;
	}
}
