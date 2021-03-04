package de.dfki.slt.frankenbot.adminbackend.db.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@javax.persistence.Entity
public class DialogNode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToOne
	@JoinColumn(name = "default_module")
	DefaultModule defaultModule;

	@ManyToOne
	@JoinColumn(name = "parent")
	@JsonBackReference
	DialogNode parent;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="parent")
	@JsonManagedReference
	List<DialogNode> children;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="node", fetch = FetchType.LAZY)
	@JsonManagedReference
	List<DialogNodeIntentCondition> dialogNodeIntentConditions;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="node", fetch = FetchType.LAZY)
	@JsonManagedReference
	List<DialogNodeTextAnswer> dialogNodeTextAnswers;

	String name;

	Integer ordering;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DialogNode getParent() {
		return parent;
	}

	public void setParent(DialogNode parent) {
		this.parent = parent;
	}

	public Integer getOrdering() {
		return ordering;
	}

	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}

	public DefaultModule getDefaultModule() {
		return defaultModule;
	}

	public void setDefaultModule(DefaultModule defaultModule) {
		this.defaultModule = defaultModule;
	}

	public List<DialogNodeIntentCondition> getDialogNodeIntentConditions() {
		return dialogNodeIntentConditions;
	}

	public void setDialogNodeIntentConditions(List<DialogNodeIntentCondition> dialogNodeIntentConditions) {
		this.dialogNodeIntentConditions = dialogNodeIntentConditions;
	}

	public List<DialogNodeTextAnswer> getDialogNodeTextAnswers() {
		return dialogNodeTextAnswers;
	}

	public void setDialogNodeTextAnswers(List<DialogNodeTextAnswer> dialogNodeTextAnswers) {
		this.dialogNodeTextAnswers = dialogNodeTextAnswers;
	}

}
