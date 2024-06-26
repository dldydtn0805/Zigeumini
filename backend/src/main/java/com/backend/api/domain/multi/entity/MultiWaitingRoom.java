package com.backend.api.domain.multi.entity;

import static lombok.AccessLevel.*;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MultiWaitingRoom {
	private Set<Long> participantIds;
	private String roomTitle;
	private Integer password;
	private Boolean isOpen;
	private Integer maxRound;
	private Map<Long, Boolean> readyState;
	@Setter
	private Long hostId;
	@Setter
	Boolean isPlaying;

	@Builder
	public MultiWaitingRoom(Set<Long> participantIds, String roomTitle, Integer password, Boolean isOpen, Integer maxRound, Map<Long, Boolean> readyState, Long hostId, Boolean isPlaying) {
		this.participantIds = participantIds;
		this.roomTitle = roomTitle;
		this.password = password;
		this.isOpen = isOpen;
		this.maxRound = maxRound;
		this.readyState = readyState;
		this.hostId = hostId;
		this.isPlaying = isPlaying;
	}
}
