package com.bankingapp.controller;

import com.bankingapp.model.Transaction;
import com.bankingapp.service.TransactionService;
import com.bankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/deposit/{accountId}")
    public String depositPage(@PathVariable Long accountId, Model model) {
        model.addAttribute("accountId", accountId);
        model.addAttribute("account", accountService.getAccountById(accountId).orElse(null));
        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long accountId, @RequestParam BigDecimal amount, @RequestParam String description, RedirectAttributes redirectAttributes) {
        try {
            transactionService.deposit(accountId, amount, description);
            redirectAttributes.addFlashAttribute("success", "Deposit successful!");
            return "redirect:/accounts/" + accountId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Deposit failed: " + e.getMessage());
            return "redirect:/transactions/deposit/" + accountId;
        }
    }

    @GetMapping("/withdraw/{accountId}")
    public String withdrawPage(@PathVariable Long accountId, Model model) {
        model.addAttribute("accountId", accountId);
        model.addAttribute("account", accountService.getAccountById(accountId).orElse(null));
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long accountId, @RequestParam BigDecimal amount, @RequestParam String description, RedirectAttributes redirectAttributes) {
        try {
            transactionService.withdraw(accountId, amount, description);
            redirectAttributes.addFlashAttribute("success", "Withdrawal successful!");
            return "redirect:/accounts/" + accountId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Withdrawal failed: " + e.getMessage());
            return "redirect:/transactions/withdraw/" + accountId;
        }
    }

    @GetMapping("/transfer/{accountId}")
    public String transferPage(@PathVariable Long accountId, Model model) {
        model.addAttribute("accountId", accountId);
        model.addAttribute("account", accountService.getAccountById(accountId).orElse(null));
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long accountId, @RequestParam String toAccountNumber, @RequestParam BigDecimal amount, @RequestParam String description, RedirectAttributes redirectAttributes) {
        try {
            transactionService.transfer(accountId, toAccountNumber, amount, description);
            redirectAttributes.addFlashAttribute("success", "Transfer successful!");
            return "redirect:/accounts/" + accountId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Transfer failed: " + e.getMessage());
            return "redirect:/transactions/transfer/" + accountId;
        }
    }
}
