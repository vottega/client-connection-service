package vottega.sse_server.client

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class RoomClient(private val webClientBuilder: WebClient.Builder) {
  fun getUserRoomList(): Mono<RoomListResponseDTO> =
    webClientBuilder.build()
      .get()
      .uri("/api/room/list")
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(RoomListResponseDTO::class.java)
}

data class RoomResponseDTO(
  val id: Long,
  val name: String,
  val ownerId: Long,
)

data class RoomListResponseDTO(
  val roomList: List<RoomResponseDTO>,
)