package hu.okfonok.service.technical;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * kiexportálja az inmemory h2db tartalmát egy fájlba
 * 
 * @author aacs
 * 
 */
@Service
@Transactional
public class ExportH2DBService {
	private EntityManager entityManager;


	@Autowired
	public ExportH2DBService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public void export(String filePath) {
		Query exportQuery = entityManager.createNativeQuery("SCRIPT TO '" + filePath + "'");
		exportQuery.getResultList();
	}
}
