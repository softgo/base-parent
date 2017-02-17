package com.application.base.test.dao;

import org.bson.types.ObjectId;

import com.application.base.mongo.morphia.MonGoBaseDao;
import com.application.base.test.BaseMorphiaDAO;
import com.application.base.test.entity.MongoMorphia;

public class MongoMorphiaDao extends BaseMorphiaDAO<MongoMorphia, ObjectId> implements MonGoBaseDao<MongoMorphia,ObjectId>{
	
}
