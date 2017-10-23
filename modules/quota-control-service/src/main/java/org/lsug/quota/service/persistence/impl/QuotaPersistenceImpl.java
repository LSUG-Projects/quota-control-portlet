/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.lsug.quota.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import org.lsug.quota.exception.NoSuchQuotaException;
import org.lsug.quota.model.Quota;
import org.lsug.quota.model.impl.QuotaImpl;
import org.lsug.quota.model.impl.QuotaModelImpl;
import org.lsug.quota.service.persistence.QuotaPersistence;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the quota service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see QuotaPersistence
 * @see org.lsug.quota.service.persistence.QuotaUtil
 * @generated
 */
@ProviderType
public class QuotaPersistenceImpl extends BasePersistenceImpl<Quota>
	implements QuotaPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link QuotaUtil} to access the quota persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = QuotaImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, QuotaImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, QuotaImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_CN_CP = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, QuotaImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByCN_CP",
			new String[] { Long.class.getName(), Long.class.getName() },
			QuotaModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			QuotaModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CN_CP = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CP",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the quota where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchQuotaException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching quota
	 * @throws NoSuchQuotaException if a matching quota could not be found
	 */
	@Override
	public Quota findByCN_CP(long classNameId, long classPK)
		throws NoSuchQuotaException {
		Quota quota = fetchByCN_CP(classNameId, classPK);

		if (quota == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchQuotaException(msg.toString());
		}

		return quota;
	}

	/**
	 * Returns the quota where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching quota, or <code>null</code> if a matching quota could not be found
	 */
	@Override
	public Quota fetchByCN_CP(long classNameId, long classPK) {
		return fetchByCN_CP(classNameId, classPK, true);
	}

	/**
	 * Returns the quota where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching quota, or <code>null</code> if a matching quota could not be found
	 */
	@Override
	public Quota fetchByCN_CP(long classNameId, long classPK,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CN_CP,
					finderArgs, this);
		}

		if (result instanceof Quota) {
			Quota quota = (Quota)result;

			if ((classNameId != quota.getClassNameId()) ||
					(classPK != quota.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_QUOTA_WHERE);

			query.append(_FINDER_COLUMN_CN_CP_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CN_CP_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Quota> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CN_CP,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"QuotaPersistenceImpl.fetchByCN_CP(long, long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Quota quota = list.get(0);

					result = quota;

					cacheResult(quota);

					if ((quota.getClassNameId() != classNameId) ||
							(quota.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CN_CP,
							finderArgs, quota);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CN_CP, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Quota)result;
		}
	}

	/**
	 * Removes the quota where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the quota that was removed
	 */
	@Override
	public Quota removeByCN_CP(long classNameId, long classPK)
		throws NoSuchQuotaException {
		Quota quota = findByCN_CP(classNameId, classPK);

		return remove(quota);
	}

	/**
	 * Returns the number of quotas where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching quotas
	 */
	@Override
	public int countByCN_CP(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CN_CP;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_QUOTA_WHERE);

			query.append(_FINDER_COLUMN_CN_CP_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CN_CP_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CN_CP_CLASSNAMEID_2 = "quota.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_CN_CP_CLASSPK_2 = "quota.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CN = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, QuotaImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCN",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, QuotaImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCN",
			new String[] { Long.class.getName() },
			QuotaModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CN = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_CN = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByCN",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the quotas where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching quotas
	 */
	@Override
	public List<Quota> findByCN(long classNameId) {
		return findByCN(classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the quotas where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @return the range of matching quotas
	 */
	@Override
	public List<Quota> findByCN(long classNameId, int start, int end) {
		return findByCN(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the quotas where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching quotas
	 */
	@Override
	public List<Quota> findByCN(long classNameId, int start, int end,
		OrderByComparator<Quota> orderByComparator) {
		return findByCN(classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the quotas where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching quotas
	 */
	@Override
	public List<Quota> findByCN(long classNameId, int start, int end,
		OrderByComparator<Quota> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN;
			finderArgs = new Object[] { classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CN;
			finderArgs = new Object[] { classNameId, start, end, orderByComparator };
		}

		List<Quota> list = null;

		if (retrieveFromCache) {
			list = (List<Quota>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (Quota quota : list) {
					if ((classNameId != quota.getClassNameId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_QUOTA_WHERE);

			query.append(_FINDER_COLUMN_CN_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(QuotaModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first quota in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching quota
	 * @throws NoSuchQuotaException if a matching quota could not be found
	 */
	@Override
	public Quota findByCN_First(long classNameId,
		OrderByComparator<Quota> orderByComparator) throws NoSuchQuotaException {
		Quota quota = fetchByCN_First(classNameId, orderByComparator);

		if (quota != null) {
			return quota;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchQuotaException(msg.toString());
	}

	/**
	 * Returns the first quota in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching quota, or <code>null</code> if a matching quota could not be found
	 */
	@Override
	public Quota fetchByCN_First(long classNameId,
		OrderByComparator<Quota> orderByComparator) {
		List<Quota> list = findByCN(classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last quota in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching quota
	 * @throws NoSuchQuotaException if a matching quota could not be found
	 */
	@Override
	public Quota findByCN_Last(long classNameId,
		OrderByComparator<Quota> orderByComparator) throws NoSuchQuotaException {
		Quota quota = fetchByCN_Last(classNameId, orderByComparator);

		if (quota != null) {
			return quota;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchQuotaException(msg.toString());
	}

	/**
	 * Returns the last quota in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching quota, or <code>null</code> if a matching quota could not be found
	 */
	@Override
	public Quota fetchByCN_Last(long classNameId,
		OrderByComparator<Quota> orderByComparator) {
		int count = countByCN(classNameId);

		if (count == 0) {
			return null;
		}

		List<Quota> list = findByCN(classNameId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the quotas before and after the current quota in the ordered set where classNameId = &#63;.
	 *
	 * @param quotaId the primary key of the current quota
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next quota
	 * @throws NoSuchQuotaException if a quota with the primary key could not be found
	 */
	@Override
	public Quota[] findByCN_PrevAndNext(long quotaId, long classNameId,
		OrderByComparator<Quota> orderByComparator) throws NoSuchQuotaException {
		Quota quota = findByPrimaryKey(quotaId);

		Session session = null;

		try {
			session = openSession();

			Quota[] array = new QuotaImpl[3];

			array[0] = getByCN_PrevAndNext(session, quota, classNameId,
					orderByComparator, true);

			array[1] = quota;

			array[2] = getByCN_PrevAndNext(session, quota, classNameId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Quota getByCN_PrevAndNext(Session session, Quota quota,
		long classNameId, OrderByComparator<Quota> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_QUOTA_WHERE);

		query.append(_FINDER_COLUMN_CN_CLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(QuotaModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(quota);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Quota> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the quotas where classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameIds the class name IDs
	 * @return the matching quotas
	 */
	@Override
	public List<Quota> findByCN(long[] classNameIds) {
		return findByCN(classNameIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the quotas where classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameIds the class name IDs
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @return the range of matching quotas
	 */
	@Override
	public List<Quota> findByCN(long[] classNameIds, int start, int end) {
		return findByCN(classNameIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the quotas where classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameIds the class name IDs
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching quotas
	 */
	@Override
	public List<Quota> findByCN(long[] classNameIds, int start, int end,
		OrderByComparator<Quota> orderByComparator) {
		return findByCN(classNameIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the quotas where classNameId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching quotas
	 */
	@Override
	public List<Quota> findByCN(long[] classNameIds, int start, int end,
		OrderByComparator<Quota> orderByComparator, boolean retrieveFromCache) {
		if (classNameIds == null) {
			classNameIds = new long[0];
		}
		else if (classNameIds.length > 1) {
			classNameIds = ArrayUtil.unique(classNameIds);

			Arrays.sort(classNameIds);
		}

		if (classNameIds.length == 1) {
			return findByCN(classNameIds[0], start, end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { StringUtil.merge(classNameIds) };
		}
		else {
			finderArgs = new Object[] {
					StringUtil.merge(classNameIds),
					
					start, end, orderByComparator
				};
		}

		List<Quota> list = null;

		if (retrieveFromCache) {
			list = (List<Quota>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_CN,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Quota quota : list) {
					if (!ArrayUtil.contains(classNameIds, quota.getClassNameId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_QUOTA_WHERE);

			if (classNameIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_CN_CLASSNAMEID_7);

				query.append(StringUtil.merge(classNameIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(QuotaModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_CN,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_CN,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the quotas where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByCN(long classNameId) {
		for (Quota quota : findByCN(classNameId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(quota);
		}
	}

	/**
	 * Returns the number of quotas where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching quotas
	 */
	@Override
	public int countByCN(long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CN;

		Object[] finderArgs = new Object[] { classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_QUOTA_WHERE);

			query.append(_FINDER_COLUMN_CN_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of quotas where classNameId = any &#63;.
	 *
	 * @param classNameIds the class name IDs
	 * @return the number of matching quotas
	 */
	@Override
	public int countByCN(long[] classNameIds) {
		if (classNameIds == null) {
			classNameIds = new long[0];
		}
		else if (classNameIds.length > 1) {
			classNameIds = ArrayUtil.unique(classNameIds);

			Arrays.sort(classNameIds);
		}

		Object[] finderArgs = new Object[] { StringUtil.merge(classNameIds) };

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_CN,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_QUOTA_WHERE);

			if (classNameIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_CN_CLASSNAMEID_7);

				query.append(StringUtil.merge(classNameIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_CN,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_CN,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CN_CLASSNAMEID_2 = "quota.classNameId = ?";
	private static final String _FINDER_COLUMN_CN_CLASSNAMEID_7 = "quota.classNameId IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CN = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, QuotaImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CN = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, QuotaImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CN",
			new String[] { Long.class.getName(), Long.class.getName() },
			QuotaModelImpl.COMPANYID_COLUMN_BITMASK |
			QuotaModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_CN = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CN",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_CN = new FinderPath(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_CN",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the quotas where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching quotas
	 */
	@Override
	public List<Quota> findByC_CN(long companyId, long classNameId) {
		return findByC_CN(companyId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the quotas where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @return the range of matching quotas
	 */
	@Override
	public List<Quota> findByC_CN(long companyId, long classNameId, int start,
		int end) {
		return findByC_CN(companyId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the quotas where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching quotas
	 */
	@Override
	public List<Quota> findByC_CN(long companyId, long classNameId, int start,
		int end, OrderByComparator<Quota> orderByComparator) {
		return findByC_CN(companyId, classNameId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the quotas where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching quotas
	 */
	@Override
	public List<Quota> findByC_CN(long companyId, long classNameId, int start,
		int end, OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CN;
			finderArgs = new Object[] { companyId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CN;
			finderArgs = new Object[] {
					companyId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<Quota> list = null;

		if (retrieveFromCache) {
			list = (List<Quota>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (Quota quota : list) {
					if ((companyId != quota.getCompanyId()) ||
							(classNameId != quota.getClassNameId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_QUOTA_WHERE);

			query.append(_FINDER_COLUMN_C_CN_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CN_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(QuotaModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching quota
	 * @throws NoSuchQuotaException if a matching quota could not be found
	 */
	@Override
	public Quota findByC_CN_First(long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator) throws NoSuchQuotaException {
		Quota quota = fetchByC_CN_First(companyId, classNameId,
				orderByComparator);

		if (quota != null) {
			return quota;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchQuotaException(msg.toString());
	}

	/**
	 * Returns the first quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching quota, or <code>null</code> if a matching quota could not be found
	 */
	@Override
	public Quota fetchByC_CN_First(long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator) {
		List<Quota> list = findByC_CN(companyId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching quota
	 * @throws NoSuchQuotaException if a matching quota could not be found
	 */
	@Override
	public Quota findByC_CN_Last(long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator) throws NoSuchQuotaException {
		Quota quota = fetchByC_CN_Last(companyId, classNameId, orderByComparator);

		if (quota != null) {
			return quota;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchQuotaException(msg.toString());
	}

	/**
	 * Returns the last quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching quota, or <code>null</code> if a matching quota could not be found
	 */
	@Override
	public Quota fetchByC_CN_Last(long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator) {
		int count = countByC_CN(companyId, classNameId);

		if (count == 0) {
			return null;
		}

		List<Quota> list = findByC_CN(companyId, classNameId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the quotas before and after the current quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param quotaId the primary key of the current quota
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next quota
	 * @throws NoSuchQuotaException if a quota with the primary key could not be found
	 */
	@Override
	public Quota[] findByC_CN_PrevAndNext(long quotaId, long companyId,
		long classNameId, OrderByComparator<Quota> orderByComparator)
		throws NoSuchQuotaException {
		Quota quota = findByPrimaryKey(quotaId);

		Session session = null;

		try {
			session = openSession();

			Quota[] array = new QuotaImpl[3];

			array[0] = getByC_CN_PrevAndNext(session, quota, companyId,
					classNameId, orderByComparator, true);

			array[1] = quota;

			array[2] = getByC_CN_PrevAndNext(session, quota, companyId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Quota getByC_CN_PrevAndNext(Session session, Quota quota,
		long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_QUOTA_WHERE);

		query.append(_FINDER_COLUMN_C_CN_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_CN_CLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(QuotaModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(quota);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Quota> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the quotas where companyId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameIds the class name IDs
	 * @return the matching quotas
	 */
	@Override
	public List<Quota> findByC_CN(long companyId, long[] classNameIds) {
		return findByC_CN(companyId, classNameIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the quotas where companyId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameIds the class name IDs
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @return the range of matching quotas
	 */
	@Override
	public List<Quota> findByC_CN(long companyId, long[] classNameIds,
		int start, int end) {
		return findByC_CN(companyId, classNameIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the quotas where companyId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameIds the class name IDs
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching quotas
	 */
	@Override
	public List<Quota> findByC_CN(long companyId, long[] classNameIds,
		int start, int end, OrderByComparator<Quota> orderByComparator) {
		return findByC_CN(companyId, classNameIds, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the quotas where companyId = &#63; and classNameId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching quotas
	 */
	@Override
	public List<Quota> findByC_CN(long companyId, long[] classNameIds,
		int start, int end, OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache) {
		if (classNameIds == null) {
			classNameIds = new long[0];
		}
		else if (classNameIds.length > 1) {
			classNameIds = ArrayUtil.unique(classNameIds);

			Arrays.sort(classNameIds);
		}

		if (classNameIds.length == 1) {
			return findByC_CN(companyId, classNameIds[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { companyId, StringUtil.merge(classNameIds) };
		}
		else {
			finderArgs = new Object[] {
					companyId, StringUtil.merge(classNameIds),
					
					start, end, orderByComparator
				};
		}

		List<Quota> list = null;

		if (retrieveFromCache) {
			list = (List<Quota>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CN,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Quota quota : list) {
					if ((companyId != quota.getCompanyId()) ||
							!ArrayUtil.contains(classNameIds,
								quota.getClassNameId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_QUOTA_WHERE);

			query.append(_FINDER_COLUMN_C_CN_COMPANYID_2);

			if (classNameIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_C_CN_CLASSNAMEID_7);

				query.append(StringUtil.merge(classNameIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(QuotaModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CN,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CN,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the quotas where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByC_CN(long companyId, long classNameId) {
		for (Quota quota : findByC_CN(companyId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(quota);
		}
	}

	/**
	 * Returns the number of quotas where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching quotas
	 */
	@Override
	public int countByC_CN(long companyId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_CN;

		Object[] finderArgs = new Object[] { companyId, classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_QUOTA_WHERE);

			query.append(_FINDER_COLUMN_C_CN_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CN_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of quotas where companyId = &#63; and classNameId = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameIds the class name IDs
	 * @return the number of matching quotas
	 */
	@Override
	public int countByC_CN(long companyId, long[] classNameIds) {
		if (classNameIds == null) {
			classNameIds = new long[0];
		}
		else if (classNameIds.length > 1) {
			classNameIds = ArrayUtil.unique(classNameIds);

			Arrays.sort(classNameIds);
		}

		Object[] finderArgs = new Object[] {
				companyId, StringUtil.merge(classNameIds)
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_CN,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_QUOTA_WHERE);

			query.append(_FINDER_COLUMN_C_CN_COMPANYID_2);

			if (classNameIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_C_CN_CLASSNAMEID_7);

				query.append(StringUtil.merge(classNameIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_CN,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_CN,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_CN_COMPANYID_2 = "quota.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_CN_CLASSNAMEID_2 = "quota.classNameId = ?";
	private static final String _FINDER_COLUMN_C_CN_CLASSNAMEID_7 = "quota.classNameId IN (";

	public QuotaPersistenceImpl() {
		setModelClass(Quota.class);
	}

	/**
	 * Caches the quota in the entity cache if it is enabled.
	 *
	 * @param quota the quota
	 */
	@Override
	public void cacheResult(Quota quota) {
		entityCache.putResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaImpl.class, quota.getPrimaryKey(), quota);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CN_CP,
			new Object[] { quota.getClassNameId(), quota.getClassPK() }, quota);

		quota.resetOriginalValues();
	}

	/**
	 * Caches the quotas in the entity cache if it is enabled.
	 *
	 * @param quotas the quotas
	 */
	@Override
	public void cacheResult(List<Quota> quotas) {
		for (Quota quota : quotas) {
			if (entityCache.getResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
						QuotaImpl.class, quota.getPrimaryKey()) == null) {
				cacheResult(quota);
			}
			else {
				quota.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all quotas.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(QuotaImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the quota.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Quota quota) {
		entityCache.removeResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaImpl.class, quota.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((QuotaModelImpl)quota, true);
	}

	@Override
	public void clearCache(List<Quota> quotas) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Quota quota : quotas) {
			entityCache.removeResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
				QuotaImpl.class, quota.getPrimaryKey());

			clearUniqueFindersCache((QuotaModelImpl)quota, true);
		}
	}

	protected void cacheUniqueFindersCache(QuotaModelImpl quotaModelImpl) {
		Object[] args = new Object[] {
				quotaModelImpl.getClassNameId(), quotaModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_CN_CP, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CN_CP, args, quotaModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(QuotaModelImpl quotaModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					quotaModelImpl.getClassNameId(), quotaModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CN_CP, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CN_CP, args);
		}

		if ((quotaModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CN_CP.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					quotaModelImpl.getOriginalClassNameId(),
					quotaModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CN_CP, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CN_CP, args);
		}
	}

	/**
	 * Creates a new quota with the primary key. Does not add the quota to the database.
	 *
	 * @param quotaId the primary key for the new quota
	 * @return the new quota
	 */
	@Override
	public Quota create(long quotaId) {
		Quota quota = new QuotaImpl();

		quota.setNew(true);
		quota.setPrimaryKey(quotaId);

		quota.setCompanyId(companyProvider.getCompanyId());

		return quota;
	}

	/**
	 * Removes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param quotaId the primary key of the quota
	 * @return the quota that was removed
	 * @throws NoSuchQuotaException if a quota with the primary key could not be found
	 */
	@Override
	public Quota remove(long quotaId) throws NoSuchQuotaException {
		return remove((Serializable)quotaId);
	}

	/**
	 * Removes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the quota
	 * @return the quota that was removed
	 * @throws NoSuchQuotaException if a quota with the primary key could not be found
	 */
	@Override
	public Quota remove(Serializable primaryKey) throws NoSuchQuotaException {
		Session session = null;

		try {
			session = openSession();

			Quota quota = (Quota)session.get(QuotaImpl.class, primaryKey);

			if (quota == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchQuotaException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(quota);
		}
		catch (NoSuchQuotaException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Quota removeImpl(Quota quota) {
		quota = toUnwrappedModel(quota);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(quota)) {
				quota = (Quota)session.get(QuotaImpl.class,
						quota.getPrimaryKeyObj());
			}

			if (quota != null) {
				session.delete(quota);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (quota != null) {
			clearCache(quota);
		}

		return quota;
	}

	@Override
	public Quota updateImpl(Quota quota) {
		quota = toUnwrappedModel(quota);

		boolean isNew = quota.isNew();

		QuotaModelImpl quotaModelImpl = (QuotaModelImpl)quota;

		Session session = null;

		try {
			session = openSession();

			if (quota.isNew()) {
				session.save(quota);

				quota.setNew(false);
			}
			else {
				quota = (Quota)session.merge(quota);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!QuotaModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { quotaModelImpl.getClassNameId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CN, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN,
				args);

			args = new Object[] {
					quotaModelImpl.getCompanyId(),
					quotaModelImpl.getClassNameId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_CN, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CN,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((quotaModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						quotaModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CN, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN,
					args);

				args = new Object[] { quotaModelImpl.getClassNameId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CN, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN,
					args);
			}

			if ((quotaModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CN.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						quotaModelImpl.getOriginalCompanyId(),
						quotaModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_CN, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CN,
					args);

				args = new Object[] {
						quotaModelImpl.getCompanyId(),
						quotaModelImpl.getClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_CN, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CN,
					args);
			}
		}

		entityCache.putResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
			QuotaImpl.class, quota.getPrimaryKey(), quota, false);

		clearUniqueFindersCache(quotaModelImpl, false);
		cacheUniqueFindersCache(quotaModelImpl);

		quota.resetOriginalValues();

		return quota;
	}

	protected Quota toUnwrappedModel(Quota quota) {
		if (quota instanceof QuotaImpl) {
			return quota;
		}

		QuotaImpl quotaImpl = new QuotaImpl();

		quotaImpl.setNew(quota.isNew());
		quotaImpl.setPrimaryKey(quota.getPrimaryKey());

		quotaImpl.setQuotaId(quota.getQuotaId());
		quotaImpl.setCompanyId(quota.getCompanyId());
		quotaImpl.setClassNameId(quota.getClassNameId());
		quotaImpl.setClassPK(quota.getClassPK());
		quotaImpl.setQuotaAssigned(quota.getQuotaAssigned());
		quotaImpl.setQuotaUsed(quota.getQuotaUsed());
		quotaImpl.setQuotaStatus(quota.getQuotaStatus());
		quotaImpl.setQuotaAlert(quota.getQuotaAlert());

		return quotaImpl;
	}

	/**
	 * Returns the quota with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the quota
	 * @return the quota
	 * @throws NoSuchQuotaException if a quota with the primary key could not be found
	 */
	@Override
	public Quota findByPrimaryKey(Serializable primaryKey)
		throws NoSuchQuotaException {
		Quota quota = fetchByPrimaryKey(primaryKey);

		if (quota == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchQuotaException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return quota;
	}

	/**
	 * Returns the quota with the primary key or throws a {@link NoSuchQuotaException} if it could not be found.
	 *
	 * @param quotaId the primary key of the quota
	 * @return the quota
	 * @throws NoSuchQuotaException if a quota with the primary key could not be found
	 */
	@Override
	public Quota findByPrimaryKey(long quotaId) throws NoSuchQuotaException {
		return findByPrimaryKey((Serializable)quotaId);
	}

	/**
	 * Returns the quota with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the quota
	 * @return the quota, or <code>null</code> if a quota with the primary key could not be found
	 */
	@Override
	public Quota fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
				QuotaImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Quota quota = (Quota)serializable;

		if (quota == null) {
			Session session = null;

			try {
				session = openSession();

				quota = (Quota)session.get(QuotaImpl.class, primaryKey);

				if (quota != null) {
					cacheResult(quota);
				}
				else {
					entityCache.putResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
						QuotaImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
					QuotaImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return quota;
	}

	/**
	 * Returns the quota with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param quotaId the primary key of the quota
	 * @return the quota, or <code>null</code> if a quota with the primary key could not be found
	 */
	@Override
	public Quota fetchByPrimaryKey(long quotaId) {
		return fetchByPrimaryKey((Serializable)quotaId);
	}

	@Override
	public Map<Serializable, Quota> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Quota> map = new HashMap<Serializable, Quota>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Quota quota = fetchByPrimaryKey(primaryKey);

			if (quota != null) {
				map.put(primaryKey, quota);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
					QuotaImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (Quota)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_QUOTA_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (Quota quota : (List<Quota>)q.list()) {
				map.put(quota.getPrimaryKeyObj(), quota);

				cacheResult(quota);

				uncachedPrimaryKeys.remove(quota.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(QuotaModelImpl.ENTITY_CACHE_ENABLED,
					QuotaImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the quotas.
	 *
	 * @return the quotas
	 */
	@Override
	public List<Quota> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @return the range of quotas
	 */
	@Override
	public List<Quota> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of quotas
	 */
	@Override
	public List<Quota> findAll(int start, int end,
		OrderByComparator<Quota> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of quotas
	 * @param end the upper bound of the range of quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of quotas
	 */
	@Override
	public List<Quota> findAll(int start, int end,
		OrderByComparator<Quota> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Quota> list = null;

		if (retrieveFromCache) {
			list = (List<Quota>)finderCache.getResult(finderPath, finderArgs,
					this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_QUOTA);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_QUOTA;

				if (pagination) {
					sql = sql.concat(QuotaModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Quota>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the quotas from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Quota quota : findAll()) {
			remove(quota);
		}
	}

	/**
	 * Returns the number of quotas.
	 *
	 * @return the number of quotas
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_QUOTA);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return QuotaModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the quota persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(QuotaImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_QUOTA = "SELECT quota FROM Quota quota";
	private static final String _SQL_SELECT_QUOTA_WHERE_PKS_IN = "SELECT quota FROM Quota quota WHERE quotaId IN (";
	private static final String _SQL_SELECT_QUOTA_WHERE = "SELECT quota FROM Quota quota WHERE ";
	private static final String _SQL_COUNT_QUOTA = "SELECT COUNT(quota) FROM Quota quota";
	private static final String _SQL_COUNT_QUOTA_WHERE = "SELECT COUNT(quota) FROM Quota quota WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "quota.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Quota exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Quota exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(QuotaPersistenceImpl.class);
}