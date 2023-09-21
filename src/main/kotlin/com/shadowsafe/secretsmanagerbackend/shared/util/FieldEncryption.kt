package com.shadowsafe.secretsmanagerbackend.shared.util
//import javax.crypto.Cipher
//import javax.crypto.SecretKey
//import javax.crypto.spec.GCMParameterSpec
//import javax.crypto.spec.SecretKeySpec
//import java.security.SecureRandom
//import java.util.Base64
//
//object FieldEncryption {
//    fun encrypt(plaintext: String, encryptionKey: String): String {
//        val iv = ByteArray(12)
//        SecureRandom().nextBytes(iv)
//
//        val keyBytes = encryptionKey.toByteArray(Charsets.UTF_8)
//        val secretKey = SecretKeySpec(keyBytes, "AES")
//
//        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
//        val parameterSpec = GCMParameterSpec(128, iv)
//
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec)
//        val encryptedBytes = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
//
//        val combined = ByteArray(iv.size + encryptedBytes.size)
//        System.arraycopy(iv, 0, combined, 0, iv.size)
//        System.arraycopy(encryptedBytes, 0, combined, iv.size, encryptedBytes.size)
//
//        return Base64.getEncoder().encodeToString(combined)
//    }
//
//    fun decrypt(encryptedText: String, encryptionKey: String): String {
//        val combined = Base64.getDecoder().decode(encryptedText)
//        val iv = ByteArray(12)
//        val encryptedBytes = ByteArray(combined.size - 12)
//
//        System.arraycopy(combined, 0, iv, 0, 12)
//        System.arraycopy(combined, 12, encryptedBytes, 0, encryptedBytes.size)
//
//        val keyBytes = encryptionKey.toByteArray(Charsets.UTF_8)
//        val secretKey = SecretKeySpec(keyBytes, "AES")
//
//        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
//        val parameterSpec = GCMParameterSpec(128, iv)
//
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec)
//        val decryptedBytes = cipher.doFinal(encryptedBytes)
//
//        return String(decryptedBytes, Charsets.UTF_8)
//    }
//}
//
