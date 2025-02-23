package vottega.sse_server.dto.mapper

import org.springframework.stereotype.Component
import vottega.sse_server.avro.VotePaperAvro
import vottega.sse_server.dto.VotePaperDTO
import java.time.ZoneId

//TODO 이거 해야됨
@Component
class VotePaperMapper {
  fun toVotePaperDTO(votePaper: VotePaper): VotePaperDTO {
    return VotePaperDTO(
      votePaperId = votePaper.id ?: throw IllegalStateException("VotePaper's id is null"),
      voteId = votePaper.vote.id ?: throw IllegalStateException("VotePaper's vote.id is null"),
      roomId = votePaper.vote.roomId,
      userId = votePaper.userId,
      userName = votePaper.userName,
      votePaperType = votePaper.votePaperType,
      createdAt = votePaper.createdAt ?: throw IllegalStateException("VotePaper's createdAt is null"),
      votedAt = votePaper.votedAt,
    )
  }

  fun toVotePaperAvro(votePaperDTO: VotePaperDTO): VotePaperAvro {
    return VotePaperAvro.newBuilder()
      .setId(votePaperDTO.votePaperId)
      .setVoteId(votePaperDTO.voteId)
      .setUserId(votePaperDTO.userId)
      .setUserName(votePaperDTO.userName)
      .setVotePaperType(votePaperTypeToAvroEnum(votePaperDTO.votePaperType))
      .setCreatedAt(votePaperDTO.createdAt.atZone(ZoneId.systemDefault()).toInstant())
      .setVotedAt(votePaperDTO.votedAt?.atZone(ZoneId.systemDefault())?.toInstant())
      .build()
  }

  fun votePaperTypeToAvroEnum(votePaperType: VotePaperType): vottega.vote_service.avro.VotePaperType {
    return when (votePaperType) {
      VotePaperType.YES -> vottega.vote_service.avro.VotePaperType.YES
      VotePaperType.NO -> vottega.vote_service.avro.VotePaperType.NO
      VotePaperType.ABSTAIN -> vottega.vote_service.avro.VotePaperType.ABSTAIN
      VotePaperType.NOT_VOTED -> vottega.vote_service.avro.VotePaperType.NOT_VOTED
    }
  }
}

