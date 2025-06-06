package vottega.sse_server.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import vottega.sse_server.dto.ClientRole
import java.util.*

class CustomParticipantRoleAuthenticationToken(
  private val participantUUID: UUID,
  val roomId: Long,
) : AbstractAuthenticationToken(listOf(SimpleGrantedAuthority(ClientRole.PARTICIPANT.roleName))) {
  init {
    isAuthenticated = true
  }

  override fun getCredentials(): Any = ClientRole.PARTICIPANT.roleName
  override fun getPrincipal(): Any = participantUUID
}