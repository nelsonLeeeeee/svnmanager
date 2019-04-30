package com.wellhope.svnmanager.dao;

import com.wellhope.svnmanager.entity.ReferInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author nelsonLee
 * @date 2018/10/23 18:03
 */

public interface ReferRepository extends JpaRepository<ReferInfo,Long> {


    @Query(value = "select refer from ReferInfo refer where refer.status = 1 and refer.userName = ?1 ORDER BY refer.referTime desc")
    List<ReferInfo> getReferInfos(String userName);

    List<ReferInfo> findByStatusOrderByReferTimeDesc(int status);

    @Query(value = "update refer set status = ?1 , auditor = ?3 , audit_time = ?4 where id = ?2", nativeQuery = true)
    @Modifying
    int audit(int status, Long referId, String auditor, Long currTime);

//    @Query(value = "update refer set expire_time = ?2 where id = ?1",nativeQuery = true)
//    @Modifying
//    int updateReferExpireTime(Long referId,Long expireTime);

    @Query(value = "select refer from ReferInfo refer where refer.userName = ?1 and (refer.status = 3 or refer.status = 4) order by refer.referTime desc")
    List<ReferInfo> getFinishRefer(String userName);

    List<ReferInfo> findByUserNameAndStatusAndExpireTimeLessThan(String userName, int status, long currTime);

}
