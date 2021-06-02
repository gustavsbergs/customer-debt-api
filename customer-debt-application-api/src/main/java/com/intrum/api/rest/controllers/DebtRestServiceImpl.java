package com.intrum.api.rest.controllers;

import com.intrum.api.rest.DebtRestService;
import com.intrum.debt.DebtService;
import com.intrum.debt.dto.DebtDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "Debt API", tags = {"Debt API"}, description = "/customer-debt-rs/v1")
@RequestMapping(path = "/customer-debt-rs/v1")
public class DebtRestServiceImpl implements DebtRestService {

    private DebtService debtService;

    @Override
    @ResponseBody
    @PostMapping(value = "/debts")
    @ApiOperation(value = "Creates a new debt", response = DebtDto.class)
    public ResponseEntity<DebtDto> createDebt(@RequestBody DebtDto debtDto) {
        return ResponseEntity.ok(debtService.createDebt(debtDto));
    }

    @Override
    @ResponseBody
    @PutMapping(value = "/debts/{id}")
    @ApiOperation(value = "Updates an existing debt")
    public ResponseEntity<Void> updateDebt(@PathVariable(value = "id") Long id, @RequestBody DebtDto debtDto) {
        debtService.updateDebt(debtDto, id);
        return ResponseEntity.ok().build();
    }

    @Override
    @ResponseBody
    @DeleteMapping(value = "/debts/{id}")
    @ApiOperation(value = "Deletes an existing debt")
    public ResponseEntity<Void> deleteDebt(@PathVariable(value = "id") Long id) {
        debtService.deleteDebt(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @ResponseBody
    @GetMapping(path = "/debts/{id}")
    @ApiOperation(value = "Finds a debt by id", response = DebtDto.class)
    public ResponseEntity<DebtDto> findDebtById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(debtService.findDebtById(id));
    }

    public void setDebtService(DebtService debtService) {
        this.debtService = debtService;
    }
}
