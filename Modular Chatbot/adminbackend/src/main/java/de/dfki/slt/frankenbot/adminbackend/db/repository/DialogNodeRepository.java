package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNode;

public interface DialogNodeRepository extends CrudRepository<DialogNode, Long> {
	@Override
	List<DialogNode> findAll();
	DialogNode findOneById(Long dialogNodeId);
	List<DialogNode> findByDefaultModule(DefaultModule module);
}
