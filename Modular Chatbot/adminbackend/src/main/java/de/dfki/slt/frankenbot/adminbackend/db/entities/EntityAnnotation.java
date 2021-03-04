package de.dfki.slt.frankenbot.adminbackend.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntityAnnotation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToOne
	@JoinColumn(name = "created_by")
	User createdBy;

	Integer beginIndex;

	Integer endIndex;

	@ManyToOne
	@JoinColumn(name = "entity_value")
	EntityValue entityValue;

	@ManyToOne
	@JoinColumn(name = "utterance")
	Utterance utterance;

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

	public Integer getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	public EntityValue getEntityValue() {
		return entityValue;
	}

	public void setEntityValue(EntityValue entityValue) {
		this.entityValue = entityValue;
	}

	public Utterance getUtterance() {
		return utterance;
	}

	public void setUtterance(Utterance utterance) {
		this.utterance = utterance;
	}

}
