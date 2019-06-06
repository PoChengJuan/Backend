package com.csi_system.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

@Service
@RepositoryRestResource
public interface ShopInfoRepository extends JpaRepository<ShopInfo, Integer>{
	
	//@Query("SELECT * FROM ShopInfo WHERE Owner LIKE ?1")
	//public String getMenunew(String name);
	@Query(value="select * from ShopInfo where Owner like ?1",nativeQuery = true)
	List<ShopInfo> aaa(String name);
	@Query(value="select StockItem from shopInfo where ShopName like ?1 and Branch like ?2",nativeQuery = true)
	String getMenu(String shopname, String branch);

	@Query(value="select Branch from shopInfo where ShopName like ?1",nativeQuery = true)
	Iterable<String> getBranch(String shopname);
}
