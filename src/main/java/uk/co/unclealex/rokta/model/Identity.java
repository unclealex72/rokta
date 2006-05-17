package uk.co.unclealex.rokta.model;

import java.io.Serializable;

public abstract class Identity<I extends Identity> implements Serializable, Comparable<I>{

	private Long i_id;

	public Long getId() {
		return i_id;
	}

	public void setId(Long id) {
		i_id = id;
	}

	public int compareTo(I o) {
		return getId().compareTo(o.getId());
	}

	public boolean equals(Object obj) {
		return obj != null && getClass().isAssignableFrom(obj.getClass()) && getId().equals((Identity) obj);
	}
	
}
