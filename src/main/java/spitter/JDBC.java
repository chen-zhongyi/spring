package spitter;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 陈忠意 on 2017/7/25.
 */
@Component
public class JDBC {

    private static final String SQL_INSTER_SPITTER =
            "insert into spitter (username, password, firstname, lastname) value (?, ?, ?, ?)";
    @Autowired
    private BasicDataSource ds;

    public void addSpitter(Spitter spitter){
        PreparedStatement s = null;
        Connection conn = null;
        try{
            conn = ds.getConnection();
            s = conn.prepareStatement(SQL_INSTER_SPITTER);
            s.setString(1, spitter.getUserName());
            s.setString(2, spitter.getPassword());
            s.setString(3, spitter.getFirstName());
            s.setString(4, spitter.getLastName());
            s.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (s != null) {
                    s.close();
                }
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
