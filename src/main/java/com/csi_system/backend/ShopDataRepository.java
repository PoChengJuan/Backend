package com.csi_system.backend;

import java.sql.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import net.sf.json.JSONArray;

@RepositoryRestResource
public interface ShopDataRepository extends CrudRepository<ShopData, Integer>{

	@Query(value="select stock from shopdata where shopname like ?1 and branch like ?2 order by shopdata.AUTO_INCREMENT desc limit 1",nativeQuery = true)
	String getLastData(String shopname, String branch);
	
	@Query(value="select stock from shopdata where shopname like ?1 and branch like ?2 order by shopdata.AUTO_INCREMENT desc limit 2",nativeQuery = true)
	JSONArray getLastDataForPut(String shopname, String branch);
	
	@Query(value="select stock from shopdata where shopname like ?1 and branch like ?2 and date like ?3",nativeQuery = true)
	String getData(String shopname, String branch, String date);
	
	@Query(value="select expense from shopdata where shopname like ?1 and branch like ?2 order by shopdata.AUTO_INCREMENT desc limit 1",nativeQuery = true)
	String getLastExpense(String shopname, String branch);
	
	@Query(value="select expense from shopdata where shopname like ?1 and branch like ?2 and date like ?3",nativeQuery = true)
	String getExpense(String shopname, String branch, String date);
	
	@Query(value="select income from shopdata where shopname like ?1 and branch like ?2 order by shopdata.AUTO_INCREMENT desc limit 1",nativeQuery = true)
	String getLastIncome(String shopname, String branch);
	
	@Query(value="select income from shopdata where shopname like ?1 and branch like ?2 and date like ?3",nativeQuery = true)
	String getIncome(String shopname, String branch, String date);
	
	@Query(value="select date from shopdata where shopname like ?1 and branch like ?2 order by shopdata.AUTO_INCREMENT desc limit 1",nativeQuery = true)
	Date getLastUploadDate(String shopname, String branch);
	
	@Query(value="SELECT Date FROM ShopData WHERE shopname LIKE ?1 AND Branch LIKE ?2 AND Date BETWEEN ?3 AND ?4",nativeQuery = true)
	Iterable<String> getRangeDate(String shopname, String branch, String startdate, String enddate);
	
	@Query(value="SELECT Income FROM ShopData WHERE shopname LIKE ?1 AND Branch LIKE ?2 AND Date BETWEEN ?3 AND ?4",nativeQuery = true)
	Iterable<String> getIncomeData(String shopname, String branch, String startdate, String enddate);
	
	@Query(value="SELECT expense FROM ShopData WHERE shopname LIKE ?1 AND Branch LIKE ?2 AND Date BETWEEN ?3 AND ?4",nativeQuery = true)
	JSONArray getExpenseData(String shopname, String branch, String startdate, String enddate);
	
	@Query(value="SELECT stock FROM ShopData WHERE shopname LIKE ?1 AND Branch LIKE ?2 AND Date BETWEEN ?3 AND ?4",nativeQuery = true)
	JSONArray getStockData(String shopname, String branch, String startdate, String enddate);
	
	@Query(value="select stock from shopdata where shopname like ?1 and branch like ?2 order by shopdata.AUTO_INCREMENT desc limit 1",nativeQuery = true)
	JSONArray getStockItem(String shopname, String branch);

	@Query(value="select stock from shopdata where shopname like ?1 and branch like ?2 and Date like %?3%",nativeQuery = true)
	JSONArray getAchieving(String shopname, String branch, String month);
	
	@Query(value="select stock from shopdata where shopname like ?1 and branch like ?2 and Date like %?3% order by shopdata.AUTO_INCREMENT desc limit 1",nativeQuery = true)
	JSONArray getLastDayofLastMonth(String shopname, String branch, String lastmonth);
	
	/*********getTodayData********************************************************************************************/
	@Query(value="select AUTO_INCREMENT from shopdata where shopname like ?1 and branch like ?2 and Date like ?3",nativeQuery = true)
	JSONArray getTodayData(String shopname, String branch, String today);
	
	/*********getMonthIncome********************************************************************************************/
	@Query(value="select income from shopdata where shopname like ?1 and branch like ?2 and Date like %?3%",nativeQuery = true)
	JSONArray getMonthIncome(String shopname, String branch, String month);
	
	/*********getMonthExpense********************************************************************************************/
	@Query(value="select expense from shopdata where shopname like ?1 and branch like ?2 and Date like %?3%",nativeQuery = true)
	JSONArray getMonthExpense(String shopname, String branch, String month);
	
	/*********getMonthsStock********************************************************************************************/
	@Query(value="select stock from shopdata where shopname like ?1 and branch like ?2 and Date like %?3%",nativeQuery = true)
	JSONArray getMonthsStock(String shopname, String branch, String month);
	
	/*********getMonthScrap********************************************************************************************/
	//JSONArray getMonthScrap(String shopname, String branch, String month);
	
	/*********getYearIncome********************************************************************************************/
	//JSONArray getYearIncome(String shopname, String branch, String year);
	
	/*********getYearExpense********************************************************************************************/
	//JSONArray getYearExpense(String shopname, String branch, String year);
	
	/*********getYearOrder********************************************************************************************/
	//JSONArray getYearOrder(String shopname, String branch, String year);
	
	/*********getYearScrap********************************************************************************************/
	//JSONArray getYearScrap(String shopname, String branch, String year);
	
	@Query(value="INSERT INTO ShopData (AUTO_INCREMENT, shopname, Branch, Date, Stock, Expense, Income) VALUES "
									   + "(NULL, 		?1,   ?2, 	  ?3,   ?4,    ?5, 		?6);",nativeQuery = true)
	String addHistoryData(String name,String branch,String date,String stock,int expense,int income);
}
