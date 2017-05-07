package at.fh.swenga.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.GalaxyModel;

public interface GalaxyRepository extends JpaRepository<GalaxyModel, Integer> {

	@Transactional
	GalaxyModel findFirstByName(String galaxyName);
	
}
