package com.csi_system.backend;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ShopDataRepository extends CrudRepository<ShopData, Integer>{

	@Query(value="select stock from shopdata where name like ?1 and branch like ?2 order by shopdata.AUTO_INCREMENT desc limit 1",nativeQuery = true)
	String getLastData(String shopname, String branch);
	
	@Query(value="SELECT Date,Stock FROM ShopData WHERE Name LIKE ?1 AND Branch LIKE ?2 AND Date BETWEEN ?3 AND ?4",nativeQuery = true)
	Iterable<String> getHistoryData(String shopname, String branch, String startdate, String enddate);
	
	@Query(value="INSERT INTO ShopData (AUTO_INCREMENT, Name, Branch, Date, Stock, Expense, Income) VALUES "
									   + "(NULL, 		?1,   ?2, 	  ?3,   ?4,    ?5, 		?6);",nativeQuery = true)
	String addHistoryData(String name,String branch,String date,String stock,int expense,int income);
}
