package vottega.sse_server.repository

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Repository
class RoomParticipantRepository {
  private val roomParticipantMap =
    ConcurrentHashMap<Long, MutableSet<Any>>()

  fun addParticipant(roomId: Long, userId: Any): Mono<Set<Any>> =
    Mono.fromCallable {
      roomParticipantMap.compute(roomId) { _, set ->
        (set ?: ConcurrentHashMap.newKeySet<Any>()).apply { add(userId) }
      }!!.toSet()
    }

  fun removeParticipant(roomId: Long, userId: Any): Mono<Set<Any>> =
    Mono.fromCallable {
      roomParticipantMap.computeIfPresent(roomId) { _, set ->
        set.remove(userId)
        if (set.isEmpty()) null else set
      }?.toSet() ?: emptySet()
    }
}