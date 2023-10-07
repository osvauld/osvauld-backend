package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository

import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.GroupAccessEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GroupAccessRepository : MongoRepository<GroupAccessEntity, String> {

    @Query("{ accessList: { \$elemMatch: { userId: ?0 } } }")
    fun findAccessContainingUser(userId: String): List<GroupAccessEntity>?
}
