package com.raines.store.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.json.JSONObject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


/**
 * mysql工具类（有连接池
 */
public class MysqlGroupConnectManager {

	static final String jdbcUrl = "jdbc:mysql://172.29.32.20:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
	static final String jdbcUsername = "learn";
	static final String jdbcPassword = "learnpassword";
	static HikariConfig config = new HikariConfig();

	static {
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(jdbcUsername);
		config.setPassword(jdbcPassword);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "100");
		config.addDataSourceProperty("maximumPoolSize", "10");
	}

	private volatile static MysqlGroupConnectManager singleton = null;
	private static Connection con = null;

	private MysqlGroupConnectManager() {
		try {
			con = new HikariDataSource(config).getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MysqlGroupConnectManager getInstance() {
		if (singleton == null) {
			synchronized (MysqlGroupConnectManager.class) {
				if (singleton == null) {
					singleton = new MysqlGroupConnectManager();
				}
			}
		}
		return singleton;
	}
	
	public Connection getConn(){
		if(con == null){
			try {
				//连接数据库..
				con = new HikariDataSource(config).getConnection();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	
	
	public void closeConn(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void executeMysql(String sql, Boolean closeConn){
		getConn();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(closeConn)
			closeConn();
	}
	
	public List<Map<String, Object>> queryMysql(String columns, String tableName, String whereSql, Boolean closeConn){
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		getConn();
		try {
			String sql = "select "+columns+" from "+tableName;
			if(!StringTool.isEmpty(whereSql)){
				sql += " where "+whereSql;
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {
		    	Map<String, Object> ele = new HashMap<String, Object>();
				
				int count = 1;
				for(String s : columns.split(",")){
					if(s.contains(" as ")){
						ele.put(s.split(" as ")[1], rs.getObject(count++));
					}else{
						ele.put(s, rs.getObject(count++));
					}
				}
				res.add(ele);	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(closeConn)
			closeConn();
		return res;
	}
	
	public List<String> querySingleField(String column, String tableName, String whereSql, Boolean closeConn){
		List<String> res = new ArrayList<String>();
		getConn();
		try {
			String sql = "select "+column+" from "+tableName;
			if(!StringTool.isEmpty(whereSql)){
				sql += " where "+whereSql;
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {	
		    	res.add(rs.getObject(1).toString());	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(closeConn)
			closeConn();
		return res;
	}
	
	public String querySingleFieldToString(String column, String tableName, String whereSql, Boolean closeConn){
		StringBuffer sb = new StringBuffer();
		getConn();
		try {
			String sql = "select "+column+" from "+tableName;
			if(!StringTool.isEmpty(whereSql)){
				sql += " where "+whereSql;
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {	
		    	sb.append(rs.getObject(1).toString());	
		    	sb.append(";");
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(closeConn)
			closeConn();
		return sb.toString();
	}
	
	public Map<String,JSONObject> listAllData(String pkColumn, String otherColumns, String tableName, String whereSql, Boolean closeConn) throws Exception {
		
		getConn();
		Map<String,JSONObject> _map = new LinkedHashMap<String,JSONObject>();
		
		String sql = "select "+pkColumn+","+otherColumns+" from "+tableName;
		if(!StringTool.isEmpty(whereSql)){
			sql += " where "+whereSql;
		}

		//执行查询
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
	    while (rs.next()) {
	    	JSONObject jsObject = new JSONObject();
			if(pkColumn.contains(" as ")){
				jsObject.put(pkColumn.split(" as ")[1], rs.getObject(1));
			}else{
				jsObject.put(pkColumn, rs.getObject(1));
			}
			
			int count = 1;
			for(String s : otherColumns.split(",")){
				if(s.contains(" as ")){
					jsObject.put(s.split(" as ")[1], rs.getObject(++count));
				}else{
					jsObject.put(s, rs.getObject(++count));
				}
			}
			_map.put(rs.getObject(1).toString(), jsObject);
	    	
	    }
	    if(closeConn)
	    	closeConn();
		
		return _map;
	}
}
