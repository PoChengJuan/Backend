package com.csi_system.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Integer> {

	@Query(value="SELECT * FROM UserDB WHERE Name LIKE ?1 AND Password LIKE ?2",nativeQuery = true)
	Iterable<User> getMember(String name, String password);
	//@Query(value="select * from ShopInfo where Owner like ?1",nativeQuery = true)
	//List<ShopInfo> aaa(String name);
	@Query(value="select LastUpLoad,isUpLoad form UserDb where Name like ?1 and Shopname like ?2",nativeQuery = true)
	String getLastUpLoad(String name, String Shopname);
	@Query(value="update UserDB set LastUpLoad = ?1, isUpLoad = ?2 where Name like ?3 and Shopname like ?4 and Branch like ?5", nativeQuery = true)
	String setLastUpload(String lastupload, Boolean isupload, String name, String shopname, String branch);
}
