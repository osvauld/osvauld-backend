package com.shadowsafe.secretsmanagerbackend.security.service

import com.shadowsafe.secretsmanagerbackend.usermanagement.repository.UsersRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UsersRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val userEntity = userRepository.findUserByEmail(s)
        if (userEntity.isEmpty) throw UsernameNotFoundException("Username not found")
        val user = userEntity.get()
        if (!user.isActive) throw UsernameNotFoundException("User is not active")
        val authorities = ArrayList<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(if (user.isAdmin) "Admin" else "User"))
        return User(
            user.email,
            user.password,
            authorities,
        )
    }
}
