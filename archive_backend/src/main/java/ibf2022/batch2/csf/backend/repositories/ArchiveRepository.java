package ibf2022.batch2.csf.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.ArchiveUpload;

@Repository
public class ArchiveRepository {
	@Autowired private MongoTemplate mongoTemplate;

	private static final String ARCHIVES_COLLECTION_NAME = "archives";

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	/* 
	 * db.getCollection("bundles").insertOne({
	 * 	"bundleId": "bundleId",
	 *	etc...
	 * })
	 */
	public ArchiveUpload recordBundle(ArchiveUpload au) {
		try { 
			return this.mongoTemplate.insert(au, ARCHIVES_COLLECTION_NAME);
		} catch (Exception ex) {
			throw ex;
		}
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	/* 
	 * db.getCollection("bundles").findOne({
	 * 	bundleId: bundleId
	 * })
	 */
	public ArchiveUpload getBundleByBundleId(Integer bundleId) {
		Query q = new Query()
			.addCriteria(Criteria.where("bundleId").is(bundleId));
		try {
			return this.mongoTemplate.findOne(q, ArchiveUpload.class, ARCHIVES_COLLECTION_NAME);
		} catch (Exception ex) {
			throw ex;
		}
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundles(/* any number of parameters here */) {
		return null;
	}


}
