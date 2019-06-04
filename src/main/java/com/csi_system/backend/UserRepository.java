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
}
