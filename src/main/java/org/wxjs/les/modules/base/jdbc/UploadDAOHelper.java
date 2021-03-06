package org.wxjs.les.modules.base.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.Util;

public class UploadDAOHelper{
	
    private static final Logger logger = Logger.getLogger(UploadDAOHelper.class);
    
    private static boolean DEBUG = true;
    
	/**
	 * use this method to run a single sql clause
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static int executeSQL(String sql) throws SQLException {
		int rst = 0;
	    
		Connection conn = null;
		Statement st = null;
		try{
			conn = getConnection();
			st = conn.createStatement();
			rst = st.executeUpdate(sql);
		}catch(SQLException ex){
			logger.error("executeSQL(String sql) errr, [SQL]:"+sql, ex);
			throw ex;
		}finally{
			if(st!=null){
				try{
					st.close();
				}catch(SQLException ex1){
					logger.error("", ex1);
				}
			}
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException ex1){
					logger.error("", ex1);
				}
			}
		}
		return rst;
	}
	
	public static int executeSQLQuietly(String sql) {
		int effects = 0;
		try {
			effects = executeSQL(sql);
		} catch (SQLException e) {
			logger.error("", e);
		}
		return effects;
	}
	
	/**
	 * use this method to run multiple sql clauses
	 * @param sqls
	 * @return
	 * @throws SQLException
	 */
	public static int executeSQL(List<String> sqls) throws SQLException {
		int rst = 0;
		if(sqls==null || sqls.size()==0){
			return rst;
		}
		Connection conn = null;
		Statement st = null;
		String sql = "";
		try{
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			for(Iterator<String> it=sqls.iterator();it.hasNext();){
				sql = it.next();
				rst += st.executeUpdate(sql);
			}
			conn.commit();
		}catch(SQLException ex){
			if(conn!=null){
				try{
					conn.rollback();
				}catch(SQLException ex1){
					logger.error("", ex1);
				}
			}
			logger.error("executeSQL(List) errr, [SQL]:"+sql, ex);
			throw ex;
		}finally{
			if(st!=null){
				try{
					st.close();
				}catch(SQLException ex1){
					logger.error("", ex1);
				}
			}
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException ex1){
					logger.error("", ex1);
				}
			}
		}
		return rst;
	}
	
	/**
	 * query, not throw exception, only return a empty Collection
	 * @param sql
	 * @return
	 */
	public static Collection<HashMap<String, Object>> queryQuietly(String sql){
		Collection<HashMap<String, Object>> col = new ArrayList<HashMap<String, Object>>();
		try {
			col = query(sql);
		} catch (SQLException e) {
			//log already written in method query()
		}
		return col;
	}
	
	public static Collection<HashMap<String, Object>> queryQuietly(String sql, boolean debug){
		Collection<HashMap<String, Object>> col = new ArrayList<HashMap<String, Object>>();
		try {
			col = query(sql, debug);
		} catch (SQLException e) {
			//log already written in method query()
		}
		return col;
	}
	
	public static Collection<HashMap<String, Object>> queryQuietlyNoLog(String sql){
		Collection<HashMap<String, Object>> col = new ArrayList<HashMap<String, Object>>();
		try {
			col = query(sql);
		} catch (SQLException e) {
			//log already written in method query()
		}
		return col;
	}
	
	/**
	 * use this method to query data
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static Collection<HashMap<String, Object>> query(String sql) throws SQLException {
		return query(sql, DEBUG);
	}
	
	public static Collection<HashMap<String, Object>> query(String sql, boolean debug) throws SQLException {
		Collection<HashMap<String, Object>> col = new ArrayList<HashMap<String, Object>>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			st = conn.prepareStatement(sql,
					  ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_READ_ONLY);
			rs =st.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				HashMap<String,Object> record = new HashMap<String,Object>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String key = rsmd.getColumnLabel(i).toUpperCase();
 					if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
						record.put(key, rs.getString(i));
					}else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
						String str = Util.getString(rs.getObject(i));
						int dot_index = str.indexOf(".");
						if(dot_index>0){
							str = str.substring(0, dot_index);
						}
						record.put(key, str);
					}else {
						record.put(key, rs.getObject(i));
					}
				}
				col.add(record);
			} 
			if(debug){
				logger.debug("sql: "+sql+", result size: "+col.size());
			}
		}catch(SQLException ex){
			logger.error("query(sql) errr, [SQL]:"+sql, ex);
			throw ex;
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(SQLException ex1){
					logger.error("", ex1);
				}
			}
			if(st!=null){
				try{
					st.close();
				}catch(SQLException ex1){
					logger.error("", ex1);
				}
			}
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException ex1){
					logger.error("", ex1);
				}
			}
		}
		
		return col;
	}		
	
	private static Connection getConnection() throws SQLException{
	    
	    try {
			Class.forName(Global.getConfig("upload.jdbc.driver")).newInstance();
		} catch (InstantiationException e) {
			throw new SQLException(e);
		} catch (IllegalAccessException e) {
			throw new SQLException(e);
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
	    String url = Global.getConfig("upload.jdbc.url");
	    String user = Global.getConfig("upload.jdbc.username");
	    String password = Global.getConfig("upload.jdbc.password");
	    return DriverManager.getConnection(url, user, password);
		
	}

}


