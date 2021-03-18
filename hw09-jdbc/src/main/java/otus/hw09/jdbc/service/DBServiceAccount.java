package otus.hw09.jdbc.service;

import otus.hw09.jdbc.models.Account;

import java.util.Optional;

public interface DBServiceAccount {
    long saveAccount(Account account);

    Optional<Account> getAccount(long id);
}
