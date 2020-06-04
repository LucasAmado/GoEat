package com.salesianostriana.dam.apigoeat.models

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Usuario")
data class User(

        @Column(nullable = false, unique = true)
        var email: String,

        var nickName: String,

        var nombreCompleto: String,

        private var password: String,

        var avatar: String? = "",

        @ElementCollection(fetch = FetchType.EAGER)
        var roles: MutableSet<String> = HashSet(),

        var fechaCreacion: LocalDate? = null,

        var fechaCambio: LocalDate? = null,

        @ManyToOne
        var bar: Bar? = null,

        private val nonExpired: Boolean = true,

        private val nonLocked: Boolean = true,

        private val enabled: Boolean = true,

        private val credentialsNonExpired: Boolean = true,

        @Id @GeneratedValue
        val id : UUID? = null


): UserDetails {

        constructor(email: String, username: String, nombreCompleto: String, password: String, avatar: String, role: String, fechaCreacion: LocalDate?, fechaCambio: LocalDate?, bar: Bar?):
                this(email, username, nombreCompleto, password, avatar, mutableSetOf(role), fechaCreacion, fechaCambio, bar, true, true, true, true)

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
                roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

        override fun isEnabled() = enabled
        override fun getUsername() = email
        override fun isCredentialsNonExpired() = credentialsNonExpired
        override fun getPassword() = password
        override fun isAccountNonExpired() = nonExpired
        override fun isAccountNonLocked() = nonLocked


        override fun equals(other: Any?): Boolean {
                if (this === other)
                        return true
                if (other === null || other !is User)
                        return false
                if (this::class != other::class)
                        return false
                return id == other.id
        }

        override fun hashCode(): Int {
                if (id == null)
                        return super.hashCode()
                return id.hashCode()
        }

}