package hu.okfonok.ad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobCategoryRepo extends JpaRepository<JobCategory, Long>{
	//TODO cache
	@Query("select jc from JobCategory jc where jc.main = 1")
	List<JobCategory> findAllMain();
	//TODO cache	
	@Query("select jc from JobCategory jc where jc.main = 0")
	List<JobCategory> findAllSub();

	JobCategory findByName(String name);
}
