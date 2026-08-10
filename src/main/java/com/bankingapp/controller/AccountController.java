package com.bankingapp.controller;

import com.bankingapp.model.Account;
import com.bankingapp.model.User;
import com.bankingapp.service.AccountService;
import com.bankingapp.service.UserService;
import com.bankingapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public String listAccounts(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName()).orElse(null);
        List<Account> accounts = accountService.getUserAccounts(user.getId());
        model.addAttribute("accounts", accounts);
        model.addAttribute("user", user);
        return "accounts";
    }

    @GetMapping("/create")
    public String createAccountPage() {
        return "create-account";
    }

    @PostMapping("/create")
    public String createAccount(@RequestParam String accountType, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName()).orElse(null);
            accountService.createAccount(user, accountType);
            redirectAttributes.addFlashAttribute("success", "Account created successfully!");
            return "redirect:/accounts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create account: " + e.getMessage());
            return "redirect:/accounts/create";
        }
    }

    @GetMapping("/{id}")
    public String viewAccount(@PathVariable Long id, Authentication authentication, Model model) {
        Account account = accountService.getAccountById(id).orElse(null);
        if (account == null) {
            return "redirect:/accounts";
        }
        model.addAttribute("account", account);
        model.addAttribute("transactions", transactionService.getAccountTransactions(id));
        return "account-detail";
    }
}
