package de.dfki.slt.frankenbot.adminbackend.db.entities;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@javax.persistence.Entity
public class AuthToken {

	@Id
	String token;

	@ManyToOne
	@JoinColumn(name = "created_by")
	User user;

	Date creationDate;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
