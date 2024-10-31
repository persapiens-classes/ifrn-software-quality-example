package org.persapiens.account.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerEquityAccountInitialValue {

    private BigDecimal value;

    private Owner owner;

    private EquityAccount EquityAccount;

}
