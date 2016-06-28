package com.nano.portal.tools;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nano.jpa.entity.Settings;
import com.nano.jpa.entity.Settings_;
import com.nano.jpa.entity.Settlement;
import com.nano.jpa.entity.Settlement_;
import com.nano.jpa.entity.Thresholds;
import com.nano.jpa.entity.Thresholds_;
import com.nano.jpa.entity.ras.BorrowableAmount;
import com.nano.jpa.entity.ras.BorrowableAmount_;
import com.nano.jpa.enums.SettingType;
import com.nano.jpa.enums.SettlementType;

/**
 * Manage database persistence and spooling via {@link PersistenceContext}
 * 
 * @author segz
 *
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class QueryManager {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private CriteriaBuilder criteriaBuilder;

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	private void init() {
		criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	/**
	 * Fetch {@link BorrowableAmount} by amount property.
	 * 
	 * @param amount
	 * @return {@link BorrowableAmount}
	 */
	public BorrowableAmount getBorrowableAmountByAmount(int amount) {

		CriteriaQuery<BorrowableAmount> criteriaQuery = criteriaBuilder.createQuery(BorrowableAmount.class);
		Root<BorrowableAmount> root = criteriaQuery.from(BorrowableAmount.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get(BorrowableAmount_.amount), amount));

		try {
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("No BorrowableAmount instance found with amount:" + amount);
		}

		return null;
	}

	/**
	 * Fetch {@link Settings} by {@link SettingType} property.
	 * 
	 * @param settingType
	 * @return list
	 */
	public List<Settings> getSettingsBySettingsType(SettingType settingType) {

		CriteriaQuery<Settings> criteriaQuery = criteriaBuilder.createQuery(Settings.class);
		Root<Settings> root = criteriaQuery.from(Settings.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(Settings_.type), settingType),
				criteriaBuilder.equal(root.get(Settings_.deleted), false)));

		try {
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("No settings instance found with settingType:" + settingType.name());
		}

		return null;
	}

	/**
	 * Fetch {@link Settings} by name property.
	 * 
	 * @param name
	 * @return {@link Settings}
	 */
	public Settings getSettingsByName(String name) {

		CriteriaQuery<Settings> criteriaQuery = criteriaBuilder.createQuery(Settings.class);
		Root<Settings> root = criteriaQuery.from(Settings.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get(Settings_.name), name));

		try {
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("No settings instance found with name:" + name);
		}

		return null;
	}

	/**
	 * Fetch {@link Thresholds} by name property.
	 * 
	 * @param name
	 * @return {@link Thresholds}
	 */
	public Thresholds getThresholdByName(String name) {

		CriteriaQuery<Thresholds> criteriaQuery = criteriaBuilder.createQuery(Thresholds.class);
		Root<Thresholds> root = criteriaQuery.from(Thresholds.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get(Thresholds_.name), name));

		try {
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("No Threshold instance found with name:" + name);
		}

		return null;
	}

	/**
	 * Fetch {@link Thresholds} by name property.
	 * 
	 * @param name
	 * @return {@link Thresholds}
	 */
	public Thresholds getThresholdByAmount(BigDecimal amount) {

		CriteriaQuery<Thresholds> criteriaQuery = criteriaBuilder.createQuery(Thresholds.class);
		Root<Thresholds> root = criteriaQuery.from(Thresholds.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get(Thresholds_.amount), amount));

		try {
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("No Threshold instance found with amount:" + amount);
		}

		return null;
	}

	/**
	 * Fetch Settlement by accountNumber property.
	 * 
	 * @param accountNumber
	 * @return {@link Settlement}
	 */
	public List<Settlement> getSettlementByAccountNumber(String accountNumber) {

		CriteriaQuery<Settlement> criteriaQuery = criteriaBuilder.createQuery(Settlement.class);
		Root<Settlement> root = criteriaQuery.from(Settlement.class);

		criteriaQuery.select(root);
		criteriaQuery
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(root.get(Settlement_.accountNumber), accountNumber),
						criteriaBuilder.equal(root.get(Settlement_.deleted), false))
					);
		try {
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("No Settlement instance found with accountNumber:" + accountNumber);
		}

		return null;
	}
	
	/**
	 * Fetch Settlement by accountNumber and settlementType properties.
	 * Method should not return a list with more than 1 object if business rules are implemented appropriately.
	 * 
	 * @param accountNumber
	 * @param settlementType
	 * @return {@link Settlement}
	 */
	public List<Settlement> getSettlementByAccountNumberAndSettlementType(String accountNumber, 
			SettlementType settlementType) {

		CriteriaQuery<Settlement> criteriaQuery = criteriaBuilder.createQuery(Settlement.class);
		Root<Settlement> root = criteriaQuery.from(Settlement.class);

		criteriaQuery.select(root);
		criteriaQuery
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(root.get(Settlement_.accountNumber), accountNumber), 
						criteriaBuilder.equal(root.get(Settlement_.settlementType), settlementType)
					));
		try {
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("No Settlement instance found with accountNumber:" + accountNumber + " and settlementType" + settlementType);
		}

		return null;
	}

	/**
	 * Fetch persisted entity instance by it {@link PrimaryKey}.
	 * 
	 * @param clazz
	 * @param pk
	 * @return persisted entity instance
	 */
	public <T> Object getByPk(Class<T> clazz, long pk) {

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("pk"), pk)),
				criteriaBuilder.equal(root.get("deleted"), false));

		try {
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("No " + clazz.getCanonicalName() + " exists with the pk:" + pk);
		}

		return null;
	}

	/**
	 * Fetches all persisted entity instance by class.
	 * 
	 * @param clazz
	 * @return List of all class entities
	 */
	public <T> List<T> getListByClass(Class<T> clazz) {

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("deleted"), false));

		try {
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("No " + clazz.getCanonicalName() + " exists ", e);
		}

		return null;
	}

	/**
	 * Fetches all persisted entity instance by class and orders List by
	 * {@link Property}.
	 * 
	 * @param clazz
	 * @param property
	 * @return ordered list of Class entities
	 */
	public <T> List<T> getListByClassOrderByProperty(Class<T> clazz, String property) {

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("deleted"), false));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(property)));

		try {
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("No " + clazz.getCanonicalName() + " exists or the property:" + property + " is invalid");
		}

		return null;
	}

	/**
	 * Fetch persisted entity by {@link PrimaryKey} and load {@link JoinColumn}
	 * eagerly
	 * 
	 * @param clazz
	 * @param pk
	 * @param properties
	 * @return persisted entity instance
	 */
	public <T> Object getByPkWithEagerLoading(Class<T> clazz, long pk, String... properties) {

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		for (String property : properties) {
			root.fetch(property, JoinType.LEFT);
		}

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("pk"), pk)),
				criteriaBuilder.equal(root.get("deleted"), false));

		try {
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("No " + clazz.getCanonicalName() + " exists with the pk:" + pk
					+ " or property arguments don't exist for entity");
		}

		return null;
	}

	/**
	 * Fetches all persisted entity instance by class, load specified properties
	 * eagerly.
	 * 
	 * @param clazz
	 * @param properties
	 * @return {@link List}
	 */
	public <T> List<T> getListByClassWithEagerProperties(Class<T> clazz, String... properties) {

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("deleted"), false));

		for (String property : properties) {
			root.fetch(property, JoinType.LEFT);
		}

		try {
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("No " + clazz.getCanonicalName() + " exists or one of the specified properties is invalid");
		}

		return null;
	}

	/**
	 * Execute SQL statement.
	 * 
	 * @param statement
	 */
	public void runSqlScript(String statement) {

		Query query = entityManager.createNativeQuery(statement);
		query.executeUpdate();
	}

	/**
	 * Persist entity and add entity instance to {@link EntityManager}.
	 * 
	 * @param entity
	 * @return persisted entity instance
	 */
	public <T> Object create(T entity) {

		try {
			entityManager.persist(entity);
			return entity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
		return null;
	}

	/**
	 * Merge the state of the given entity into the current
	 * {@link PersistenceContext}.
	 * 
	 * @param entity
	 * @return the managed instance that the state was merged to
	 */
	public <T> Object update(T entity) {

		try {
			entityManager.merge(entity);
			return entity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}

		return null;
	}

	/**
	 * Remove the entity instance.
	 * 
	 * @param entity
	 */
	public <T> void delete(T entity) {
		try {
			entityManager.remove(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}

}