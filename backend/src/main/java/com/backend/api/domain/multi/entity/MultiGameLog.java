package com.backend.api.domain.multi.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.backend.api.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = "multi_game_log")
@NoArgsConstructor(access = PROTECTED)
public class MultiGameLog extends BaseEntity {

	@Id
	@Column(name = "multi_game_log_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "multiGameLog")
	private List<MultiTrade> multiTrade = new ArrayList<>();

	@NotNull
	private Long gameId; // 레디스로 저장된 게임 Id

	@NotNull
	private Integer round;

	@NotNull
	private Long stockId; // 주식 Id값

	@NotNull
	private LocalDateTime startDate;

	@OneToMany(mappedBy = "multiGameLog")
	List<MultiGamePlayer> multiGamePlayers = new ArrayList<>();

	@Builder
	public MultiGameLog(List<MultiTrade> multiTrade, Long gameId, Integer round, Long stockId, LocalDateTime startDate, List<MultiGamePlayer> multiGamePlayers) {
		this.multiTrade = multiTrade;
		this.gameId = gameId;
		this.round = round;
		this.stockId = stockId;
		this.startDate = startDate;
		this.multiGamePlayers = multiGamePlayers;
	}
}
