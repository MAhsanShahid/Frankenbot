package de.dfki.slt.frankenbot.adminbackend.db.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@javax.persistence.Entity
public class DialogNodeIntentCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToOne
	@JoinColumn(name = "node")
	@JsonBackReference
	DialogNode node;

	@ManyToOne
	@JoinColumn(name = "intent")
	Intent intent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DialogNode getNode() {
		return node;
	}

	public void setNode(DialogNode node) {
		this.node = node;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
}
