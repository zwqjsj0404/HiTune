/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.chukwa.rest.services;

import java.util.*;
import java.sql.*;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.chukwa.database.DatabaseConfig;
import org.apache.hadoop.chukwa.database.Macro;
import org.apache.hadoop.chukwa.util.DatabaseWriter;

import org.apache.hadoop.chukwa.rest.objects.DfsNameNode;
import org.apache.hadoop.chukwa.rest.services.RestHome;

/**
 * Home object for domain model class DfsNameNode.
 * @see org.apahe.hadoop.chukwa.rest.objects.DfsNameNode
 * @author Hibernate Tools
 */
public class DfsNameNodeHome extends RestHome {
    private static String table="[dfs_namenode]";
    private static final Log log = LogFactory
	.getLog(DfsNameNodeHome.class);

    /*
     * convert from a result set record to an object
     */
    private static DfsNameNode createDfsNameNode(ResultSet rs) {
	DfsNameNode obj = null;
	try {
	    obj = new DfsNameNode(
					  rs.getTimestamp("timestamp"),
					  rs.getString("host"),
					  rs.getDouble("add_block_ops"),
					  rs.getDouble("blocks_corrupted"),
					  rs.getDouble("create_file_ops"),
					  rs.getDouble("delete_file_ops"),
					  rs.getDouble("files_created"),
					  rs.getDouble("files_renamed"),
					  rs.getDouble("files_deleted"),
					  rs.getDouble("get_block_locations"),
					  rs.getDouble("get_listing_ops"),
					  rs.getDouble("safe_mode_time"),
					  rs.getDouble("syncs_avg_time"),
					  rs.getDouble("syncs_num_ops"),
					  rs.getDouble("transactions_avg_time"),
					  rs.getDouble("transactions_num_ops"),
					  rs.getDouble("block_report_avg_time"),
					  rs.getDouble("block_report_num_ops"),
					  rs.getDouble("fs_image_load_time")
					  );
	} catch (Exception e) {
	}
	return obj;
    }
    
    /*
     * find by timestamp
     */
    public static DfsNameNode find(String timestamp) {
	String cluster = getCluster();
	DatabaseWriter dbw = new DatabaseWriter(cluster);

	if (timestamp != null) {
	    // get simple value
            try {
		String query = getSingleQuery(DfsNameNodeHome.table,"timestamp",timestamp);
	    	ResultSet rs = dbw.query(query);
	    	if (rs.next()) {
		    DfsNameNode obj = createDfsNameNode(rs);
		    return obj;
		}
	    } catch (Exception e) {
		log.error("exception:"+e.toString());
	    }
	} else {
	    // check start time and end time
	}
	return null;
    }

    /*
     * find by key 
     */
    public static DfsNameNode find(String timestamp, String host) {
	String cluster = getCluster();
	DatabaseWriter dbw = new DatabaseWriter(cluster);

	if (timestamp != null) {
	    // get simple value
            try {
		Map<String, String> criteriaMap = new HashMap<String,String>();
		criteriaMap.put("timestamp",convertLongToDateString(Long.parseLong(timestamp)));
		criteriaMap.put("host",host);

		String query = getCriteriaQuery(DfsNameNodeHome.table,criteriaMap);
	    	ResultSet rs = dbw.query(query);
	    	if (rs.next()) {
		    DfsNameNode obj = createDfsNameNode(rs);
		    return obj;
		}
	    } catch (Exception e) {
		log.error("exception:"+e.toString());
	    }
	} else {
	    // check start time and end time
	}
	return null;
    }

    /*
     * find within the start time and end time
     */
    public static Collection<DfsNameNode> findBetween(String starttime, String endtime) {
	String cluster = getCluster();
	DatabaseWriter dbw = new DatabaseWriter(cluster);

	Collection<DfsNameNode> collection = new Vector<DfsNameNode>();

	try {
	    String query = getTimeBetweenQuery(DfsNameNodeHome.table,starttime,endtime);	    
	    ResultSet rs = dbw.query(query);
	    while (rs.next()) {
		DfsNameNode obj = createDfsNameNode(rs);
		collection.add(obj);
	    }
	} catch (Exception e) {
	    log.error("exception:"+e.toString());
	}
	return collection;
    }
}
