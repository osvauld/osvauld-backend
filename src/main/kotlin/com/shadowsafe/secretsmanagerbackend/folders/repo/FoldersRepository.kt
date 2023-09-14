package com.shadowsafe.secretsmanagerbackend.folders.repo

import com.shadowsafe.secretsmanagerbackend.folders.model.FolderEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FoldersRepository : MongoRepository<FolderEntity, String>
