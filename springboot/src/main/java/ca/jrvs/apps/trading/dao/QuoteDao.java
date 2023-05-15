package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/*
 * The Quote DAO is responsible for CRUD Quote objects being accessed to and from the quoteTable
 */
@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    /*
     *
     * @param s
     * @return
     * @param <S>
     * @throws DataAccessException for unexpected SQL result or SQL execution failure
     */
    @Override
    public <S extends Quote> S save(S quote) {
        if (existsById(quote.getId())) {
            int updatedRowNo = updateOne(quote);
            if (updatedRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        }
        else {
            addOne(quote);
        }

        return quote;
    }

    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    private int updateOne(Quote quote) {
        String updateSql = "UPDATE " + TABLE_NAME + " SET last_price=?, bid_price=?, bid_size=?, "
            + "ask_price=?, ask_size=? WHERE " + ID_COLUMN_NAME + "=?";

        return jdbcTemplate.update(updateSql, makeUpdateValues(quote));
    }

    /**
     * Helper method that makes sql update value objects
     * @param quote to be updated
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(Quote quote) {
        Object[] quoteValues = new Object[] {
            quote.getLastPrice(),
            quote.getBidPrice(),
            quote.getBidSize(),
            quote.getAskPrice(),
            quote.getAskSize(),
            quote.getId()
        };

        return quoteValues;
    }

    /**
     * Saves all quotes to db
     * @param quotes
     * @return
     * @param <S>
     */
    @Override
    public <S extends Quote> Iterable<S> saveAll(Iterable<S> quotes) {
        Iterator<S> quotesIterator = quotes.iterator();
        ArrayList<S> quotesList = new ArrayList<S>();

        while (quotesIterator.hasNext()) {
            quotesList.add(save(quotesIterator.next()));
        }

        return quotesList;
    }

    @Override
    public Optional<Quote> findById(String id) {
        String getSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
        Optional<Quote> queryOutput = Optional.empty();

        try {
            queryOutput = Optional.ofNullable(jdbcTemplate.queryForObject(
                getSql,
                BeanPropertyRowMapper.newInstance(Quote.class),
                id
            ));
        }
        catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find ticker id:" + id, e);
        }

        return queryOutput;
    }

    @Override
    public boolean existsById(String id) {
        String existsSql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE "
            + ID_COLUMN_NAME + "=?";
        int rowCount = jdbcTemplate.queryForObject(existsSql, Integer.class, id);

        return rowCount == 1;
    }

    /**
     * Returns all quotes
     * @return
     * @throws org.springframework.dao.DataAccessException if failed to update
     */
    @Override
    public Iterable<Quote> findAll() {
        String getSql = "SELECT * FROM " + TABLE_NAME;
        List<Quote> queryOutput = new ArrayList<Quote>();

        try {
            queryOutput = jdbcTemplate.query(
                getSql,
                BeanPropertyRowMapper.newInstance(Quote.class)
            );
        }
        catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(e);
        }

        return queryOutput;
    }

    /**
     * Find a quote by ticker
     * @param iterable
     * @return
     */
    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        String countSql = "SELECT count(*) FROM " + TABLE_NAME;

        try {
            return jdbcTemplate.queryForObject(countSql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String id) {
        if (id == null || id == "") {
            throw new IllegalArgumentException("ID can't be null");
        }
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
        jdbcTemplate.update(deleteSql, id);
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {

    }
}
