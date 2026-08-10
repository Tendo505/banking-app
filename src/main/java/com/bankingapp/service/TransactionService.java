package com.bankingapp.service;

import com.bankingapp.model.Account;
import com.bankingapp.model.Transaction;
import com.bankingapp.repository.TransactionRepository;
import com.bankingapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction deposit(Long accountId, BigDecimal amount, String description) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account account = accountOpt.get();
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getBalance());
        transaction.setDescription(description);
        transaction.setStatus("COMPLETED");

        return transactionRepository.save(transaction);
    }

    public Transaction withdraw(Long accountId, BigDecimal amount, String description) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account account = accountOpt.get();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getBalance());
        transaction.setDescription(description);
        transaction.setStatus("COMPLETED");

        return transactionRepository.save(transaction);
    }

    public Transaction transfer(Long fromAccountId, String toAccountNumber, BigDecimal amount, String description) {
        Optional<Account> fromAccountOpt = accountRepository.findById(fromAccountId);
        Optional<Account> toAccountOpt = accountRepository.findByAccountNumber(toAccountNumber);

        if (fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Debit from source account
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepository.save(fromAccount);

        // Credit to target account
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(toAccount);

        // Record transaction from source account
        Transaction transaction = new Transaction();
        transaction.setAccount(fromAccount);
        transaction.setTransactionType("TRANSFER");
        transaction.setAmount(amount);
        transaction.setBalanceAfter(fromAccount.getBalance());
        transaction.setDescription(description);
        transaction.setRecipientAccountNumber(toAccountNumber);
        transaction.setStatus("COMPLETED");

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAccountTransactions(Long accountId) {
        return transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
}
