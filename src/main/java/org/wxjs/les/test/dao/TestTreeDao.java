/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.test.dao;

import org.wxjs.les.common.persistence.TreeDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author ThinkGem
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}