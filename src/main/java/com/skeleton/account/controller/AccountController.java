package com.skeleton.account.controller;

import com.skeleton.account.common.exception.AccessDeniedException;
import com.skeleton.account.common.exception.AlreadyExistException;
import com.skeleton.account.common.exception.AuthenticationException;
import com.skeleton.account.common.exception.NotFoundException;
import com.skeleton.account.dto.account.AccountDto;
import com.skeleton.account.dto.account.AccountsDto;
import com.skeleton.account.dto.account.ChangeFullNameDto;
import com.skeleton.account.dto.account.ChangePasswordDto;
import com.skeleton.account.dto.account.ChangeUsernameDto;
import com.skeleton.account.dto.account.LoginDto;
import com.skeleton.account.dto.account.LogoutDto;
import com.skeleton.account.dto.account.ReadAccountDto;
import com.skeleton.account.dto.account.RegistrationDto;
import com.skeleton.account.dto.token.TokenDto;
import com.skeleton.account.service.AccountService;
import com.skeleton.account.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.skeleton.account.common.constant.ControllerConstantUtils.DEFAULT_DIRECTION;
import static com.skeleton.account.common.constant.ControllerConstantUtils.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('r_all','r_accounts')")
    public ResponseEntity<AccountsDto> readAllAccounts() throws NotFoundException {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('d_all','d_accounts')")
    public ResponseEntity<String> deleteAllAccounts() {
        accountService.deleteAllAccounts();
        return ResponseEntity.ok(OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('r_all','r_accounts','r_self')")
    public ResponseEntity<AccountDto> readAccount(@RequestBody ReadAccountDto dto) throws NotFoundException {
        return ResponseEntity.ok(accountService.getAccount(dto.getUsername()));
    }

    @PostMapping("/registration")
    public ResponseEntity<AccountDto> createAccount(@RequestBody RegistrationDto registrationDto)
            throws AccessDeniedException, NotFoundException, AlreadyExistException {
        return ResponseEntity.ok(accountService.createAccount(registrationDto));
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAnyAuthority('d_all','d_accounts','d_self')")
    public ResponseEntity<String> deleteAccount(@PathVariable("name") String name) {
        accountService.deleteAccount(name);
        return ResponseEntity.ok(OK);
    }

    @GetMapping("/page/{page}/size/{size}")
    @PreAuthorize("hasAnyAuthority('r_all','r_accounts')")
    public ResponseEntity<AccountsDto> readAccounts(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) throws NotFoundException {
        return ResponseEntity.ok(accountService.getAllAccounts(page, size));
    }

    @GetMapping("/page/{page}/size/{size}/sort-by/{sort-by}")
    @PreAuthorize("hasAnyAuthority('r_all','r_accounts')")
    public ResponseEntity<AccountsDto> readAccounts(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size,
                                                    @PathVariable("sort-by") String sortBy) throws NotFoundException {
        return ResponseEntity.ok(accountService.getAllAccounts(page, size, DEFAULT_DIRECTION, sortBy));
    }

    @GetMapping("/page/{page}/size/{size}/sort-by/{sort-by}/direction/{direction}")
    @PreAuthorize("hasAnyAuthority('r_all','r_accounts')")
    public ResponseEntity<AccountsDto> readAccounts(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size,
                                                    @PathVariable("sort-by") String sortBy,
                                                    @PathVariable("direction") String direction)
            throws NotFoundException {
        return ResponseEntity.ok(accountService.getAllAccounts(page, size, direction, sortBy));
    }

    @PostMapping("/change/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto)
            throws NotFoundException {
        accountService.changeAccountPassword(changePasswordDto);
        return ResponseEntity.ok(OK);
    }

    @PostMapping("/change/fullname")
    @PreAuthorize("hasAnyAuthority('u_all','u_accounts','u_self')")
    public ResponseEntity<String> changeFullName(@RequestBody @Valid ChangeFullNameDto changeFullNameDto)
            throws NotFoundException {
        accountService.changeAccountFullName(changeFullNameDto);
        return ResponseEntity.ok(OK);
    }

    @PostMapping("/change/username")
    @PreAuthorize("hasAnyAuthority('u_all','u_accounts','u_self')")
    public ResponseEntity<String> changeUsername(@RequestBody @Valid ChangeUsernameDto changeUsernameDto)
            throws NotFoundException, AlreadyExistException {
        accountService.changeAccountUsername(changeUsernameDto);
        return ResponseEntity.ok(OK);
    }

    @PostMapping("/authentication/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto) throws AuthenticationException {
        TokenDto tokenDto = authenticationService.login(loginDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/authentication/logout")
    public ResponseEntity<TokenDto> logout(@RequestBody @Valid LogoutDto logoutDto)
            throws NotFoundException, AlreadyExistException {
        TokenDto tokenDto = authenticationService.logout(logoutDto);
        return ResponseEntity.ok(tokenDto);
    }
}
