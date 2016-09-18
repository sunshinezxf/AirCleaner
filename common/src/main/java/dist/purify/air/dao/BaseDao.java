package dist.purify.air.dao;

import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;

/**
 * Created by sunshine on 16/9/11.
 */
public class BaseDao {
    @Resource
    protected SqlSession sqlSession;

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
