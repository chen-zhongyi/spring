package spitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 陈忠意 on 2017/7/25.
 */
@Repository
public class JdbcSpitterRepository implements SpitterRepository{

    private static final String SQL_INSERT_SPITTER =
            "insert into spitter (username, password, firstname, lastname) value(?, ?, ?, ?)";
    private static final String SQL_UPDATE_SPITTER =
            "update spitter set username = ?, password = ?, firstname = ?, lastname = ? " +
                    " where id = ?";
    private static final String SQL_DELETE_SPITTER =
            "delete from spitter where id = ?";
    private static final String SQL_SELECT_BY_ID =
            "select * from spitter where id = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addSpitter(Spitter spitter){
        jdbcTemplate.update(SQL_INSERT_SPITTER,
                spitter.getUserName(),
                spitter.getPassword(),
                spitter.getFirstName(),
                spitter.getLastName());
    }

    public void updateSpitter(Spitter spitter){
        jdbcTemplate.update(SQL_UPDATE_SPITTER,
                spitter.getUserName(),
                spitter.getPassword(),
                spitter.getFirstName(),
                spitter.getLastName(),
                spitter.getId());
    }

    public void deleteSpitter(Long id){
        jdbcTemplate.update(SQL_DELETE_SPITTER,
                id);
    }

    public Spitter findOne(Long id){
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID,
                new SpitterRowMapper(),
                id);
    }

    private static final class SpitterRowMapper implements RowMapper<Spitter>{

        public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException{
            return new Spitter(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("firstname"),
                    rs.getString("lastname")
            );
        }
    }
}
