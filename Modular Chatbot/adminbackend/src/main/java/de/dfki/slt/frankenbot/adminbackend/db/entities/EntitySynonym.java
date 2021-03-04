package de.dfki.slt.frankenbot.adminbackend.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntitySynonym {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToOne
	@JoinColumn(name = "created_by")
	User createdBy;

	@ManyToOne
	@JoinColumn(name = "entity_value")
	EntityValue entityValue;

	String synonym;

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

	public EntityValue getEntityValue() {
		return entityValue;
	}

	public void setEntityValue(EntityValue entityValue) {
		this.entityValue = entityValue;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

}
