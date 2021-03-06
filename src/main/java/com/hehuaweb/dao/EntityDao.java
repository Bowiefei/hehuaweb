package com.hehuaweb.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hehuaweb.mapper.EntityRowMapper;
import com.hehuaweb.model.Entity;
import com.hehuaweb.util.Pair;

/**
 * @author mixueqiang
 * @since Dec 29, 2013
 */
@Repository
public class EntityDao extends BaseDao {
  private static final Log LOG = LogFactory.getLog(EntityDao.class);

  public int count(String modelName) {
    String sql = "SELECT COUNT(id) FROM " + modelName;
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
    return count == null ? 0 : count;
  }

  public int count(String modelName, Map<String, Object> condition) {
    if (MapUtils.isEmpty(condition)) {
      return count(modelName);
    }

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT COUNT(id) FROM ").append(modelName).append(" WHERE ");
    Object[] params = new Object[condition.size()];
    int index = 0;
    for (String key : condition.keySet()) {
      sb.append(key).append(" = ? AND ");
      params[index++] = condition.get(key);
    }
    sb.delete(sb.lastIndexOf(" AND "), sb.length());
    Integer count = jdbcTemplate.queryForObject(sb.toString(), Integer.class, params);
    return count == null ? 0 : count;
  }

  public int count(String modelName, String columnName, Object columnValue) {
    return count(modelName, "id", columnName, columnValue);
  }

  public int count(String modelName, String column, String columnName, Object columnValue) {
    String sql = "SELECT COUNT(" + column + ") FROM " + modelName + " WHERE " + columnName + " = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { columnValue });
    return count == null ? 0 : count;
  }

  public void delete(String modelName, Object idValue) {
    String sql = "UPDATE " + modelName + " SET status = -1 WHERE id = ?";
    jdbcTemplate.update(sql, new Object[] { idValue });
  }

  public boolean exists(String modelName, Map<String, Object> condition) {
    return count(modelName, condition) > 0;
  }

  public boolean exists(String modelName, String columnName, Object columnValue) {
    return count(modelName, columnName, columnValue) > 0;
  }

  public boolean exists(String modelName, String column, String columnName, Object columnValue) {
    return count(modelName, column, columnName, columnValue) > 0;
  }

  public List<Entity> find(String modelName, Map<String, Object> condition, int pageNumber, int pageSize) {
    return find(modelName, condition, pageNumber, pageSize, EntityRowMapper.getInstance());
  }

  public <T> List<T> find(String modelName, Map<String, Object> condition, int pageNumber, int pageSize, RowMapper<T> rowMapper) {
    return find(modelName, condition, pageNumber, pageSize, rowMapper, ORDER_OPTION_ASC);
  }

  public <T> List<T> find(String modelName, Map<String, Object> condition, int pageNumber, int pageSize, RowMapper<T> rowMapper, String orderOption) {
    return find(modelName, condition, pageNumber, pageSize, rowMapper, ORDER_BY_ID, orderOption);
  }

  public <T> List<T> find(String modelName, Map<String, Object> condition, int pageNumber, int pageSize, RowMapper<T> rowMapper, String orderBy, String orderOption) {
    pageNumber = pageNumber > 0 ? pageNumber : 1;
    if (MapUtils.isEmpty(condition)) {
      return get(modelName, pageNumber, pageSize, rowMapper, orderBy, orderOption);
    }

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM ").append(modelName).append(" WHERE ");
    Object[] params = new Object[condition.size() + 2];
    int index = 0;
    for (String key : condition.keySet()) {
      if ("typeId".equals(key)) { // Hard code for typeId query.
        sb.append(key).append(" IN (0, ?) AND ");
      } else {
        sb.append(key).append(" = ? AND ");
      }
      params[index++] = condition.get(key);
    }
    sb.delete(sb.lastIndexOf(" AND "), sb.length());
    if (StringUtils.isNotEmpty(orderOption)) {
      sb.append(" ORDER BY " + orderBy + " " + orderOption);
    }

    sb.append(" LIMIT ? OFFSET ?");
    params[index++] = pageSize;
    params[index++] = (pageNumber - 1) * pageSize;

    return jdbcTemplate.query(sb.toString(), params, rowMapper);
  }

  public List<Entity> find(String modelName, Map<String, Object> condition, int pageNumber, int pageSize, String orderOption) {
    return find(modelName, condition, pageNumber, pageSize, EntityRowMapper.getInstance(), orderOption);
  }

  public <T> List<T> find(String modelName, Map<String, Object> condition, int pageNumber, RowMapper<T> rowMapper) {
    return find(modelName, condition, pageNumber, PAGE_SIZE_MEDIUM, rowMapper, ORDER_OPTION_ASC);
  }

  public <T> List<T> find(String modelName, Map<String, Object> condition, RowMapper<T> rowMapper) {
    return find(modelName, condition, 1, PAGE_SIZE_MEDIUM, rowMapper, ORDER_OPTION_ASC);
  }

  public List<Entity> find(String modelName, String columnName, Object columnValue) {
    return find(modelName, columnName, columnValue, EntityRowMapper.getInstance());
  }

  public List<Entity> find(String modelName, String columnName, Object columnValue, int pageNumber) {
    return find(modelName, columnName, columnValue, pageNumber, PAGE_SIZE_MEDIUM);
  }

  public List<Entity> find(String modelName, String columnName, Object columnValue, int pageNumber, int pageSize) {
    return find(modelName, columnName, columnValue, EntityRowMapper.getInstance(), pageNumber, pageSize);
  }

  public <T> List<T> find(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper) {
    String sql = "SELECT * FROM " + modelName + " WHERE " + columnName + " = ?";
    return jdbcTemplate.query(sql, new Object[] { columnValue }, rowMapper);
  }

  public <T> List<T> find(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper, int pageNumber) {
    return find(modelName, columnName, columnValue, rowMapper, pageNumber, PAGE_SIZE_MEDIUM);
  }

  public <T> List<T> find(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper, int pageNumber, int pageSize) {
    pageNumber = pageNumber > 0 ? pageNumber : 1;

    String sql = "SELECT * FROM " + modelName + " WHERE " + columnName + " = ? LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new Object[] { columnValue, pageSize, (pageNumber - 1) * pageSize }, rowMapper);
  }

  public <T> List<T> find(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper, int pageNumber, int pageSize, String orderOption) {
    return find(modelName, columnName, columnValue, rowMapper, pageNumber, pageSize, ORDER_BY_ID, orderOption);
  }

  public <T> List<T> find(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper, int pageNumber, int pageSize, String orderBy, String orderOption) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM ").append(modelName).append(" WHERE ").append(columnName).append(" = ?");
    if (StringUtils.isNotEmpty(orderOption)) {
      sb.append(" ORDER BY ").append(orderBy).append(" ").append(orderOption).append(" ");
    }
    sb.append(" LIMIT ? OFFSET ?");

    return jdbcTemplate.query(sb.toString(), new Object[] { columnValue, pageSize, (pageNumber - 1) * pageSize }, rowMapper);
  }

  public <T> List<T> find(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper, int pageNumber, String orderOption) {
    return find(modelName, columnName, columnValue, rowMapper, pageNumber, PAGE_SIZE_MEDIUM, ORDER_BY_ID, orderOption);
  }

  public <T> List<T> find(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper, String orderBy, String orderOption) {
    return find(modelName, columnName, columnValue, rowMapper, 1, PAGE_SIZE_MEDIUM, ORDER_BY_ID, orderOption);
  }

  public <T> Pair<Integer, List<T>> findAndCount(String modelName, int pageNumber, int pageSize, RowMapper<T> rowMapper) {
    return findAndCount(modelName, Collections.<String, Object> emptyMap(), pageNumber, pageSize, rowMapper, ORDER_OPTION_DESC);
  }

  public <T> Pair<Integer, List<T>> findAndCount(String modelName, int pageNumber, int pageSize, RowMapper<T> rowMapper, String orderOption) {
    return findAndCount(modelName, Collections.<String, Object> emptyMap(), pageNumber, pageSize, rowMapper, orderOption);
  }

  public <T> Pair<Integer, List<T>> findAndCount(String modelName, int pageNumber, RowMapper<T> rowMapper) {
    return findAndCount(modelName, Collections.<String, Object> emptyMap(), pageNumber, PAGE_SIZE_MEDIUM, rowMapper);
  }

  public Pair<Integer, List<Entity>> findAndCount(String modelName, Map<String, Object> condition, int pageNumber, int pageSize) {
    return findAndCount(modelName, condition, pageNumber, pageSize, ORDER_OPTION_DESC);
  }

  public <T> Pair<Integer, List<T>> findAndCount(String modelName, Map<String, Object> condition, int pageNumber, int pageSize, RowMapper<T> rowMapper) {
    return findAndCount(modelName, condition, pageNumber, pageSize, rowMapper, ORDER_OPTION_DESC);
  }

  public <T> Pair<Integer, List<T>> findAndCount(String modelName, Map<String, Object> condition, int pageNumber, int pageSize, RowMapper<T> rowMapper, String orderOption) {
    int count = count(modelName, condition);
    List<T> entities = find(modelName, condition, pageNumber, pageSize, rowMapper, orderOption);
    return new Pair<Integer, List<T>>(count, entities);
  }

  public Pair<Integer, List<Entity>> findAndCount(String modelName, Map<String, Object> condition, int pageNumber, int pageSize, String orderOption) {
    return findAndCount(modelName, condition, pageNumber, pageSize, EntityRowMapper.getInstance(), orderOption);
  }

  public <T> Pair<Integer, List<T>> findAndCount(String modelName, Map<String, Object> condition, int pageNumber, RowMapper<T> rowMapper) {
    return findAndCount(modelName, condition, pageNumber, PAGE_SIZE_MEDIUM, rowMapper, ORDER_OPTION_DESC);
  }

  public List<Entity> findGreater(String modelName, String columnName, Object columnValue) {
    return findGreater(modelName, columnName, columnValue, EntityRowMapper.getInstance());
  }

  public <T> List<T> findGreater(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper) {
    String sql = "SELECT * FROM " + modelName + " WHERE " + columnName + " > ?";
    try {
      return jdbcTemplate.query(sql, new Object[] { columnValue }, rowMapper);

    } catch (Throwable t) {
      LOG.error("Error occurs when executing findGreater on " + modelName + ".", t);
      return null;
    }
  }

  public Entity findOne(String modelName, Map<String, Object> condition) {
    return findOne(modelName, condition, EntityRowMapper.getInstance());
  }

  public <T> T findOne(String modelName, Map<String, Object> condition, RowMapper<T> rowMapper) {
    List<T> entities = find(modelName, condition, 1, 1, rowMapper);
    if (CollectionUtils.isEmpty(entities)) {
      return null;
    }

    return entities.get(0);
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public Entity findOne(String modelName, String columnName, Object columnValue) {
    return findOne(modelName, columnName, columnValue, EntityRowMapper.getInstance());
  }

  public <T> T findOne(String modelName, String columnName, Object columnValue, RowMapper<T> rowMapper) {
    String sql = "SELECT * FROM " + modelName + " WHERE " + columnName + " = ?";
    try {
      return jdbcTemplate.queryForObject(sql, new Object[] { columnValue }, rowMapper);

    } catch (Throwable t) {
      LOG.error("Error occurs when executing findOne on " + modelName + ".", t);
      return null;
    }
  }

  public List<Entity> get(String modelName, int pageNumber) {
    return get(modelName, pageNumber, PAGE_SIZE_MEDIUM, EntityRowMapper.getInstance());
  }

  public <T> List<T> get(String modelName, int pageNumber, int pageSize, RowMapper<T> rowMapper) {
    return get(modelName, pageNumber, pageSize, rowMapper, ORDER_OPTION_ASC);
  }

  public <T> List<T> get(String modelName, int pageNumber, int pageSize, RowMapper<T> rowMapper, String orderOption) {
    return get(modelName, pageNumber, pageSize, rowMapper, "id", orderOption);
  }

  public <T> List<T> get(String modelName, int pageNumber, int pageSize, RowMapper<T> rowMapper, String orderBy, String orderOption) {
    pageNumber = pageNumber > 0 ? pageNumber : 1;

    String sql = null;
    if (StringUtils.isEmpty(orderBy)) {
      sql = "SELECT * FROM " + modelName + " LIMIT ? OFFSET ?";

    } else {
      if (!ORDER_OPTION_ASC.equals(orderOption) && !ORDER_OPTION_DESC.equals(orderOption)) {
        throw new IllegalArgumentException("Order option must be ASC or DESC, now it is " + orderOption);
      }
      sql = "SELECT * FROM " + modelName + " ORDER BY " + orderBy + " " + orderOption + " LIMIT ? OFFSET ?";
    }

    return jdbcTemplate.query(sql, new Object[] { pageSize, (pageNumber - 1) * pageSize }, rowMapper);
  }

  public List<Entity> get(String modelName, int pageNumber, int pageSize, String orderOption) {
    return get(modelName, pageNumber, pageSize, EntityRowMapper.getInstance(), orderOption);
  }

  public List<Entity> get(String modelName, int pageNumber, int pageSize, String orderBy, String orderOption) {
    return get(modelName, pageNumber, pageSize, EntityRowMapper.getInstance(), orderBy, orderOption);
  }

  public <T> List<T> get(String modelName, int pageNumber, RowMapper<T> rowMapper) {
    return get(modelName, pageNumber, PAGE_SIZE_MEDIUM, rowMapper);
  }

  public <T> List<T> get(String modelName, int pageNumber, RowMapper<T> rowMapper, String orderOption) {
    return get(modelName, pageNumber, PAGE_SIZE_MEDIUM, rowMapper, orderOption);
  }

  public Entity get(String modelName, Object idValue) {
    String sql = "SELECT * FROM " + modelName + " WHERE id = ?";
    return queryForNullable(sql, new Object[] { idValue }, EntityRowMapper.getInstance());
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public <T> T get(String modelName, Object idValue, RowMapper<T> rowMapper) {
    String sql = "SELECT * FROM " + modelName + " WHERE id = ?";
    return queryForNullable(sql, new Object[] { idValue }, rowMapper);
  }

  public void remove(String modelName, Object idValue) {
    String sql = "DELETE FROM " + modelName + " WHERE id = ?";
    jdbcTemplate.update(sql, new Object[] { idValue });
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public int save(Entity entity) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(entity.getModelName());
    return simpleJdbcInsert.execute(entity);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public Entity saveAndReturn(Entity entity) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(entity.getModelName()).usingGeneratedKeyColumns("id");
    Number number = simpleJdbcInsert.executeAndReturnKey(entity);
    entity.put("id", number.longValue());
    return entity;
  }

  public void update(String string, Map<String, Object> condition, Map<String, Object> values) {
    // TODO Auto-generated method stub
  }

  public int update(String modelName, Map<String, Object> condition, String updateColumnName, Object updateColumnValue) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE ").append(modelName).append(" SET ");
    Object[] params = new Object[condition.size() + 2];
    int index = 0;
    sb.append(updateColumnName).append(" = ?, updateTime = ? WHERE ");
    params[index++] = updateColumnValue;
    params[index++] = System.currentTimeMillis();

    for (String key : condition.keySet()) {
      sb.append(key).append(" = ? AND ");
      params[index++] = condition.get(key);
    }
    sb.delete(sb.lastIndexOf(" AND "), sb.length());

    return jdbcTemplate.update(sb.toString(), params);
  }

  public int update(String modelName, String columnName, Object columnValue, Map<String, Object> updateValues) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE ").append(modelName).append(" SET ");
    Object[] params = new Object[updateValues.size() + 1];
    int index = 0;
    for (String key : updateValues.keySet()) {
      sb.append(key).append(" = ?, ");
      params[index++] = updateValues.get(key);
    }
    sb.delete(sb.lastIndexOf(", "), sb.length());
    sb.append(" WHERE ").append(columnName).append(" = ?");
    params[index++] = columnValue;

    return jdbcTemplate.update(sb.toString(), params);
  }

  public int update(String modelName, String columnName, Object columnValue, String updateColumnName, Object updateColumnValue) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE ").append(modelName);
    sb.append(" SET ").append(updateColumnName).append(" = ?, updateTime = ? WHERE ").append(columnName).append(" = ?");
    return jdbcTemplate.update(sb.toString(), new Object[] { updateColumnValue, System.currentTimeMillis(), columnValue });
  }

}
