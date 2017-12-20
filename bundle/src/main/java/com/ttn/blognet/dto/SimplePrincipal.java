package com.ttn.blognet.dto;

import org.apache.commons.lang.StringUtils;

import java.security.Principal;


/**
 * Created by vishal on 18/12/17.
 */
public class SimplePrincipal implements Principal {

	protected final String name;

	public SimplePrincipal(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Principal name cannot be blank.");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override public int hashCode() {
		return name.hashCode();
	}

	@Override public boolean equals(Object obj) {
		if (obj instanceof Principal) {
			return name.equals(((Principal) obj).getName());
		}
		return false;
	}

}
