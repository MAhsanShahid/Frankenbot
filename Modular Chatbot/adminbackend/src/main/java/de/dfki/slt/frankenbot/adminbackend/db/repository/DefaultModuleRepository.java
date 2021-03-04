package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;

public interface DefaultModuleRepository extends CrudRepository<DefaultModule, Long> {

	@Override
	List<DefaultModule> findAll();

	DefaultModule findOneById(Long userId);
}
