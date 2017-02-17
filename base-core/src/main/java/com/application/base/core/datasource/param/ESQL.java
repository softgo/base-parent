package com.application.base.core.datasource.param;

/**
 * 定义一个接口. 
 */
public enum ESQL {
	
	// system sql
	SAVE("saveObject"), 
	
	SAVEBATCH("saveBatchObject"),
	
	UPDATEBYID("updateObjectByID"),
	
	UPDATEBYUUID("updateObjectByUUID"),
	
	LOGICDELETE("logicDelete"),
	
	PHYSICALDELETE("physicalDelete"),
	
	QUERYSINGLERESULTBYID("querySingleResultById"),
	
	QUERYSINGLERESULTBYUUID("querySingleResultByUUID"),
	
	QUERYSINGLERESULTBYPARAMS("querySingleResultByParams"),
	
	QUERYLISTRESULT("queryListResult"),
	
	QUERYALLLISTRESULT("queryAllListResult"),
	
	QUERYLISTRESULTCOUNT("queryListResultCount"),
	
	UPDATECUSTOMCOLUMNBYWHERE("updateCustomColumnByWhere"),

	LOGICWHEREDELETE("logicWhereDelete"),
	
	PHYSICALWHEREDELETE("physicalWhereDelete"),
	
    QUERYLISTRESULTCOUNTBYWHERE("queryListResultCountByWhere"),
    
	QUERYLISTRESULTBYWHERE("queryListResultByWhere"),
	
	QUERYLISTRESULTBYCOLUMN("queryListResultByColumn");

	private String sql;

	ESQL(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
	
}
