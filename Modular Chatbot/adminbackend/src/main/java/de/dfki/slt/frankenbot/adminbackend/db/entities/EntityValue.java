package de.dfki.slt.frankenbot.adminbackend.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntityValue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToOne
	@JoinColumn(name="created_by")
	User createdBy;
	
	@ManyToOne
	@JoinColumn(name="entity")
	de.dfki.slt.frankenbot.adminbackend.db.entities.Entity entity;
	
	String value;

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

	public de.dfki.slt.frankenbot.adminbackend.db.entities.Entity getEntity() {
		return entity;
	}

	public void setEntity(de.dfki.slt.frankenbot.adminbackend.db.entities.Entity entity) {
		this.entity = entity;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
