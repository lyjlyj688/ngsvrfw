package person.lyjyy.core.cache;

import com.ibatis.sqlmap.client.SqlMapSession;
import person.lyjyy.core.domain.InstanceObj;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yujie.li on 14-8-10.
 */
public class CoreDao4Ds {

    public <T extends InstanceObj> List<T> select(String sql, Object param) throws SQLException {
        SqlMapSession session = null;
        try {
            session = DsCache.cache.getSession();
            return session.queryForList(sql, param);
        }catch (SQLException e) {
            throw e;
        }finally {
            session.close();
        }
    }


    public <T extends InstanceObj> T selectOne(String sql, Object param) throws SQLException {
        SqlMapSession session = null;
        try {
            session = DsCache.cache.getSession();
            return (T) session.queryForObject(sql, param);
        }catch (SQLException e) {
            throw e;
        }finally {
            session.close();
        }
    }


}
