package de.dfki.slt.frankenbot.adminbackend.db.entities;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@javax.persistence.Entity
public class Chatbot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToOne
	@JoinColumn(name = "created_by")
	User createdBy;

	@ManyToMany(fetch = FetchType.EAGER)
	List<DefaultModule> modules;

	String name;
	
	String welcomeMessage;
	
	String fallbackMessage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DefaultModule> getModules() {
		return modules;
	}

	public void setModules(List<DefaultModule> modules) {
		this.modules = modules;
	}

	public String getFallbackMessage() {
		return fallbackMessage;
	}

	public void setFallbackMessage(String fallbackMessage) {
		this.fallbackMessage = fallbackMessage;
	}

	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	
	
}
