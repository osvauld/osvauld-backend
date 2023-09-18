package com.shadowsafe.secretsmanagerbackend.folders.repo

import com.shadowsafe.secretsmanagerbackend.folders.model.FolderTree
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FolderTreeRepository : MongoRepository<FolderTree, String> {
    fun findByOrganisationId(organisationId: ObjectId): FolderTree
}
