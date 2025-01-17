package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@ToString
@NoArgsConstructor
public class EntryDTO {

	private long id;

	private LocalDateTime date;

	private BigDecimal value;

	private AccountDTO inAccount;

	private AccountDTO outAccount;

	private String owner;

	private String note;

}
