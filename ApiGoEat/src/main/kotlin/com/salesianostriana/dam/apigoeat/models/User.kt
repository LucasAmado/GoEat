package com.salesianostriana.dam.apigoeat.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
data class User(
        @Column(nullable = false, unique = true)
        private var username: String,

        @Column(nullable = false, unique = true)
        var email: String,

        private var password: String,

        var avatar: String = "",

        @ElementCollection(fetch = FetchType.EAGER)
        val roles: MutableSet<String> = HashSet(),

        @JsonBackReference
        @ManyToOne
        var bar: Bar? = null,

        @JsonManagedReference
        @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
        var pedidos: MutableList<Pedido>? = null,

        private val nonExpired: Boolean = true,

        private val nonLocked: Boolean = true,

        private val enabled: Boolean = true,

        private val credentialsNonExpired: Boolean = true,

        @Id @GeneratedValue
        val id : UUID? = null


): UserDetails {

        constructor(username: String, email: String, password: String, avatar: String, role: String, bar: Bar?, pedidos: MutableList<Pedido>?):
                this(username, email, password, avatar, mutableSetOf(role), bar, pedidos, true, true, true, true)

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
                roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

        override fun isEnabled() = enabled
        override fun getUsername() = username
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