package com.talissonmelo.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.enums.TypeLaunch;

public interface LaunchRepository extends JpaRepository<Launch, Long>{
	
	@Query(value = " select sum(l.value) from Launch l join l.user u "
			+ "where u.id = :user_id and l.type = :type group by u")
	Double balanceUser(@Param("user_id") Long user_id,@Param("type") TypeLaunch type);

}
