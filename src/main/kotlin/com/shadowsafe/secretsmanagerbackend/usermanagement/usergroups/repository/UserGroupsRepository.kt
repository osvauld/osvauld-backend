package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository

import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.UserGroupsEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface UserGroupsRepository : MongoRepository<UserGroupsEntity, String>
