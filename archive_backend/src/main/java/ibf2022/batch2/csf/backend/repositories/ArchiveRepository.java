package ibf2022.batch2.csf.backend.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.ArchiveUpload;

@Repository
public class ArchiveRepository {
	@Autowired private MongoTemplate mongoTemplate;

	private static final String ARCHIVES_COLLECTION_NAME = "archives";

	/* 
	 * db.getCollection("archives").insertOne({
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

	/* 
	 * db.getCollection("archives").findOne({
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

	/* 
	 * db.getCollection("archives").find({sort: { date: -1, title: 1 }})
	 */
	public List<ArchiveUpload> getBundles() {
		Query q = new Query()
			.with(Sort.by(Direction.DESC, "date")
			.and(Sort.by(Direction.ASC, "title")));
		try {
			return this.mongoTemplate.find(q, ArchiveUpload.class, ARCHIVES_COLLECTION_NAME);
		} catch (Exception ex) {
			throw ex;
		}
	}	
}
