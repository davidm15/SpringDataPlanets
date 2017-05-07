package at.fh.swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.PlanetModel;

@Repository
@Transactional
public interface PlanetRepository extends JpaRepository<PlanetModel, Integer>  {
	
	public List<PlanetModel> findByNameContaining(String searchString);
	
	public List<PlanetModel> findByNameOrSurface(String name,String surface);

	public List<PlanetModel> doANameSearchWithLike(@Param("search") String string);

	public List<PlanetModel> findBySize(float searchString);

	public int deleteByName(String searchString);

	public List<PlanetModel> findByNameOrderBySize(String searchString);
	
	public List<PlanetModel> findByNameContainingOrSurfaceContainingAllIgnoreCase(String name, String surface);

	public List<PlanetModel> findBySizeGreaterThan20000();

	@Query("select e from EmployeeModel e where e.name = :name or e.surface = :name")
	public List<PlanetModel> findByWhateverName(@Param("name") String searchString);

	public int deleteByGalaxyName(String searchString);
	
	
	
}
